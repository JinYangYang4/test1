package com.example.bendiyinyue1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListenMusicActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView nextIv, playIv, lastIv;
    TextView singerTv, songTv;
    private Button benDi, ting, communicate, myself;
    RecyclerView musicRv;
    List<NetMusic> mDatas;
    NetMusicAdapter adapter;
    int position;
    int currentPlayPosition = -1;
    SeekBar musicSeekBar;
    MediaPlayer mediaPlayer;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_music);
        benDi = findViewById(R.id.benDi);
        ting = findViewById(R.id.ting);
        communicate = findViewById(R.id.communicate);
        myself = findViewById(R.id.myself);

        fetchNetMusicList();

        benDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListenMusicActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        // 设置听音乐按钮的点击事件
        ting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListenMusicActivity.this, ListenMusicActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        // 设置社区按钮的点击事件
        communicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListenMusicActivity.this, CommunityActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        // 设置我的按钮的点击事件
        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListenMusicActivity.this, MyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }

        });
        addTestData();
        initUI();



        mediaPlayer = new MediaPlayer();

        adapter = new NetMusicAdapter(mDatas);
        musicRv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        musicRv.setLayoutManager(layoutManager);


        addTestData();

        adapter.notifyDataSetChanged();


        setEventListener();
    }

    private void initUI() {
        nextIv = findViewById(R.id.local_music_bottom_iv_next);
        playIv = findViewById(R.id.local_music_bottom_iv_play);
        lastIv = findViewById(R.id.local_music_bottom_iv_last);
        singerTv = findViewById(R.id.local_music_bottom_tv_singer);
        songTv = findViewById(R.id.local_music_bottom_tv_song);
        musicRv = findViewById(R.id.local_music_rv);
        musicSeekBar = findViewById(R.id.musicSeekbar1);
    }


    private void setEventListener() {
        adapter.setOnItemClickListener(new NetMusicAdapter.OnItemClickListener() {
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
        NetMusic musicBean = mDatas.get(position);
        singerTv.setText(musicBean.getAlbumId());
        songTv.setText(musicBean.getFilename());
        stopMusic();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(musicBean.getUrl());
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    playIv.setImageResource(R.drawable.play);
                    musicSeekBar.setMax(mediaPlayer.getDuration());
                    startTimer();
                }
            });
            runOnUiThread(() -> {
                adapter.notifyDataSetChanged(); // 更新适配器
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playMusic() {
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

    private void fetchNetMusicList() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://mobilecdnbj.kugou.com/api/v5/special/recommend?recommend_expire=0&sign=52186982747e1404d426fa3f2a1e8ee4&plat=0&uid=0&version=9108&page=1&area_code=1&appid=1005&mid=286974383886022203545511837994020015101&_t=1545746286\n")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("酷狗音乐", responseData);
                    parseNetMusicData(responseData);
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void parseNetMusicData(String responseData) {
        try {
            JSONArray jsonArray = new JSONArray(responseData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("albumld");
                String artist = jsonObject.getString("filename");
                String url = jsonObject.getString("url");
                NetMusic music = new NetMusic(title, artist, url);
                mDatas.add(music);
            }
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                playMusic();
            }
        }
    }

    private void addTestData() {
        NetMusic music1 = new NetMusic("歌曲1", "艺术家1", "http://example.com/song1.mp3");
        NetMusic music2 = new NetMusic("歌曲2", "艺术家2", "http://example.com/song2.mp3");
        NetMusic music3 = new NetMusic("歌曲3", "艺术家3", "http://example.com/song3.mp3");
        mDatas = new ArrayList<>();
        mDatas.add(music1);
        mDatas.add(music2);
        mDatas.add(music3);
        Log.d("ListenMusicActivity", "Test data added: " + mDatas.size()); // 添加日志输出
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