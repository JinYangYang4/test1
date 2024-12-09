package com.example.bendiyinyue1;

import android.annotation.SuppressLint;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView nextIv, playIv, lastIv;
    TextView singerTv, songTv;
    private Button benDi, ting, communicate, myself;
    RecyclerView musicRv;

    List<LocalMusicBean> mDatas;
    private LocalMusicAdapter adapter;
    private int position;
    int currentPlayPosition = -1;
    MediaPlayer mediaPlayer;
    SeekBar musicSeekBar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        benDi = findViewById(R.id.benDi);
        ting = findViewById(R.id.ting);
        communicate = findViewById(R.id.communicate);
        myself = findViewById(R.id.myself);

        // 设置本地按钮的点击事件，返回 MainActivity 本身
        benDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        // 设置听音乐按钮的点击事件
        ting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListenMusicActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        // 设置社区按钮的点击事件
        communicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommunityActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        // 设置我的按钮的点击事件
        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("音乐播放器");

        initView();

        mediaPlayer = new MediaPlayer();
        mDatas = new ArrayList<>();
        adapter = new LocalMusicAdapter(this, mDatas);
        musicRv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        musicRv.setLayoutManager(layoutManager);

        // 请求权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            loadLocalMusicData();
        }

        setEventListener();
    }

    private void setEventListener() {
        adapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                currentPlayPosition = position;
                playMusic(currentPlayPosition);
            }
        });
        nextIv.setOnClickListener(this);
        lastIv.setOnClickListener(this);
        playIv.setOnClickListener(this);
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopTimer();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                startTimer();
            }
        });
    }

    private void playMusic(int position) {
        LocalMusicBean musicBean = mDatas.get(position);
        singerTv.setText(musicBean.getSinger());
        songTv.setText(musicBean.getSong());
        stopMusic();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(musicBean.getPath());
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    playIv.setImageResource(R.drawable.play);
                    musicSeekBar.setMax(mediaPlayer.getDuration());
                    startTimer();
                }
            });
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(mediaPlayer -> nextMusic());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void plsyMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            playIv.setImageResource(R.drawable.play);
            startTimer();
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            playIv.setImageResource(R.drawable.icon_play);
            stopTimer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
        stopTimer();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @SuppressLint({"Range", "NotifyDataSetChanged"})
    private void loadLocalMusicData() {
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = resolver.query(uri, null, null, null, null);
        int id = 0;
        while (cursor != null && cursor.moveToNext()) {
            String song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            id++;
            String sid = String.valueOf(id);
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String time = sdf.format(new Date(duration));
            LocalMusicBean bean = new LocalMusicBean(sid, song, singer, album, time, path);
            mDatas.add(bean);
        }
        if (cursor != null) {
            cursor.close();
        }
        adapter.notifyDataSetChanged();

    }

    private void initView() {
        nextIv = findViewById(R.id.local_music_bottom_iv_next);
        playIv = findViewById(R.id.local_music_bottom_iv_play);
        lastIv = findViewById(R.id.local_music_bottom_iv_last);
        singerTv = findViewById(R.id.local_music_bottom_tv_singer);
        songTv = findViewById(R.id.local_music_bottom_tv_song);
        musicRv = findViewById(R.id.local_music_rv);
        musicSeekBar = findViewById(R.id.musicSeekbar1);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.local_music_bottom_iv_next) {
            nextMusic();
        } else if (v.getId() == R.id.local_music_bottom_iv_last) {
            lastMusic();
        } else if (v.getId() == R.id.local_music_bottom_iv_play) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playIv.setImageResource(R.drawable.icon_play);
                stopTimer();
            } else {
                plsyMusic();
            }
        }
    }



    private void nextMusic() {
        if (currentPlayPosition < mDatas.size() - 1) {
            currentPlayPosition++;
            playMusic(currentPlayPosition);
        } else {
            // 如果已经是最后一首音乐，则可以选择从头开始播放或者停止播放
            currentPlayPosition = 0;
            playMusic(currentPlayPosition);
        }
    }

    private void lastMusic() {
        if (currentPlayPosition > 0) {
            currentPlayPosition--;
            playMusic(currentPlayPosition);
        }
    }

    private Timer timer;
    private TimerTask timerTask;

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    int progress = mediaPlayer.getCurrentPosition();
                    runOnUiThread(() -> musicSeekBar.setProgress(progress));
                }
            }
        };
        timer.schedule(timerTask, 0, 1000); // 每秒更新一次进度
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}