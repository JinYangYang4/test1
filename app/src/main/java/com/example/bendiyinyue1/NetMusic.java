package com.example.bendiyinyue1;

import java.util.List;

@lombok.NoArgsConstructor
@lombok.Data
public class NetMusic {


    @com.fasterxml.jackson.annotation.JsonProperty("data")
    private DataDTO data;
    @com.fasterxml.jackson.annotation.JsonProperty("errcode")
    private Integer errcode;
    @com.fasterxml.jackson.annotation.JsonProperty("status")
    private Integer status;
    @com.fasterxml.jackson.annotation.JsonProperty("error")
    private String error;

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class DataDTO {
        @com.fasterxml.jackson.annotation.JsonProperty("list")
        private List<ListDTO> list;
        @com.fasterxml.jackson.annotation.JsonProperty("recommend_expire")
        private Integer recommendExpire;
        @com.fasterxml.jackson.annotation.JsonProperty("next_page")
        private Integer nextPage;

        @lombok.NoArgsConstructor
        @lombok.Data
        public static class ListDTO {
            @com.fasterxml.jackson.annotation.JsonProperty("reason")
            private String reason;
            @com.fasterxml.jackson.annotation.JsonProperty("specialid")
            private Integer specialid;
            @com.fasterxml.jackson.annotation.JsonProperty("playcount")
            private Integer playcount;
            @com.fasterxml.jackson.annotation.JsonProperty("songcount")
            private Integer songcount;
            @com.fasterxml.jackson.annotation.JsonProperty("slid")
            private Integer slid;
            @com.fasterxml.jackson.annotation.JsonProperty("nickname")
            private String nickname;
            @com.fasterxml.jackson.annotation.JsonProperty("commentcount")
            private Integer commentcount;
            @com.fasterxml.jackson.annotation.JsonProperty("collectcount")
            private Integer collectcount;
            @com.fasterxml.jackson.annotation.JsonProperty("trans_param")
            private DataDTO.ListDTO.TransParamDTO transParam;
            @com.fasterxml.jackson.annotation.JsonProperty("specialname")
            private String specialname;
            @com.fasterxml.jackson.annotation.JsonProperty("percount")
            private Integer percount;
            @com.fasterxml.jackson.annotation.JsonProperty("songs")
            private List<SongsDTO> songs;
            @com.fasterxml.jackson.annotation.JsonProperty("imgurl")
            private String imgurl;
            @com.fasterxml.jackson.annotation.JsonProperty("suid")
            private Integer suid;
            @com.fasterxml.jackson.annotation.JsonProperty("from")
            private String from;
            @com.fasterxml.jackson.annotation.JsonProperty("global_specialid")
            private String globalSpecialid;
            @com.fasterxml.jackson.annotation.JsonProperty("intro")
            private String intro;
            @com.fasterxml.jackson.annotation.JsonProperty("type")
            private Integer type;

            @lombok.NoArgsConstructor
            @lombok.Data
            public static class TransParamDTO {
                @com.fasterxml.jackson.annotation.JsonProperty("special_tag")
                private Integer specialTag;
            }

            @lombok.NoArgsConstructor
            @lombok.Data
            public static class SongsDTO {
                @com.fasterxml.jackson.annotation.JsonProperty("hash")
                private String hash;
                @com.fasterxml.jackson.annotation.JsonProperty("sqfilesize")
                private Integer sqfilesize;
                @com.fasterxml.jackson.annotation.JsonProperty("sqprivilege")
                private Integer sqprivilege;
                @com.fasterxml.jackson.annotation.JsonProperty("pay_type_sq")
                private Integer payTypeSq;
                @com.fasterxml.jackson.annotation.JsonProperty("bitrate")
                private Integer bitrate;
                @com.fasterxml.jackson.annotation.JsonProperty("pkg_price_sq")
                private Integer pkgPriceSq;
                @com.fasterxml.jackson.annotation.JsonProperty("has_accompany")
                private Integer hasAccompany;
                @com.fasterxml.jackson.annotation.JsonProperty("topic_url_320")
                private String topicUrl320;
                @com.fasterxml.jackson.annotation.JsonProperty("sqhash")
                private String sqhash;
                @com.fasterxml.jackson.annotation.JsonProperty("fail_process")
                private Integer failProcess;
                @com.fasterxml.jackson.annotation.JsonProperty("pay_type")
                private Integer payType;
                @com.fasterxml.jackson.annotation.JsonProperty("rp_type")
                private String rpType;
                @com.fasterxml.jackson.annotation.JsonProperty("album_id")
                private String albumId;
                @com.fasterxml.jackson.annotation.JsonProperty("mvhash")
                private String mvhash;
                @com.fasterxml.jackson.annotation.JsonProperty("extname")
                private String extname;
                @com.fasterxml.jackson.annotation.JsonProperty("topic_url_sq")
                private String topicUrlSq;
                @com.fasterxml.jackson.annotation.JsonProperty("320hash")
                private String $320hash;
                @com.fasterxml.jackson.annotation.JsonProperty("price_sq")
                private Integer priceSq;
                @com.fasterxml.jackson.annotation.JsonProperty("inlist")
                private Integer inlist;
                @com.fasterxml.jackson.annotation.JsonProperty("m4afilesize")
                private Integer m4afilesize;
                @com.fasterxml.jackson.annotation.JsonProperty("old_cpy")
                private Integer oldCpy;
                @com.fasterxml.jackson.annotation.JsonProperty("price_320")
                private Integer price320;
                @com.fasterxml.jackson.annotation.JsonProperty("320filesize")
                private Integer $320filesize;
                @com.fasterxml.jackson.annotation.JsonProperty("pkg_price_320")
                private Integer pkgPrice320;
                @com.fasterxml.jackson.annotation.JsonProperty("album_sizable_cover")
                private String albumSizableCover;
                @com.fasterxml.jackson.annotation.JsonProperty("feetype")
                private Integer feetype;
                @com.fasterxml.jackson.annotation.JsonProperty("price")
                private Integer price;
                @com.fasterxml.jackson.annotation.JsonProperty("filename")
                private String filename;
                @com.fasterxml.jackson.annotation.JsonProperty("pkg_price")
                private Integer pkgPrice;
                @com.fasterxml.jackson.annotation.JsonProperty("remark")
                private String remark;
                @com.fasterxml.jackson.annotation.JsonProperty("fail_process_320")
                private Integer failProcess320;
                @com.fasterxml.jackson.annotation.JsonProperty("trans_param")
                private DataDTO.ListDTO.SongsDTO.TransParamDTO transParam;
                @com.fasterxml.jackson.annotation.JsonProperty("filesize")
                private Integer filesize;
                @com.fasterxml.jackson.annotation.JsonProperty("topic_url")
                private String topicUrl;
                @com.fasterxml.jackson.annotation.JsonProperty("album_audio_id")
                private Integer albumAudioId;
                @com.fasterxml.jackson.annotation.JsonProperty("brief")
                private String brief;
                @com.fasterxml.jackson.annotation.JsonProperty("rp_publish")
                private Integer rpPublish;
                @com.fasterxml.jackson.annotation.JsonProperty("privilege")
                private Integer privilege;
                @com.fasterxml.jackson.annotation.JsonProperty("audio_id")
                private Integer audioId;
                @com.fasterxml.jackson.annotation.JsonProperty("duration")
                private Integer duration;
                @com.fasterxml.jackson.annotation.JsonProperty("320privilege")
                private Integer $320privilege;
                @com.fasterxml.jackson.annotation.JsonProperty("pay_type_320")
                private Integer payType320;
                @com.fasterxml.jackson.annotation.JsonProperty("fail_process_sq")
                private Integer failProcessSq;

                @lombok.NoArgsConstructor
                @lombok.Data
                public static class TransParamDTO {
                    @com.fasterxml.jackson.annotation.JsonProperty("display_rate")
                    private Integer displayRate;
                    @com.fasterxml.jackson.annotation.JsonProperty("union_cover")
                    private String unionCover;
                    @com.fasterxml.jackson.annotation.JsonProperty("classmap")
                    private DataDTO.ListDTO.SongsDTO.TransParamDTO.ClassmapDTO classmap;
                    @com.fasterxml.jackson.annotation.JsonProperty("pay_block_tpl")
                    private Integer payBlockTpl;
                    @com.fasterxml.jackson.annotation.JsonProperty("qualitymap")
                    private DataDTO.ListDTO.SongsDTO.TransParamDTO.QualitymapDTO qualitymap;
                    @com.fasterxml.jackson.annotation.JsonProperty("songname_suffix")
                    private String songnameSuffix;
                    @com.fasterxml.jackson.annotation.JsonProperty("ipmap")
                    private DataDTO.ListDTO.SongsDTO.TransParamDTO.IpmapDTO ipmap;
                    @com.fasterxml.jackson.annotation.JsonProperty("language")
                    private String language;
                    @com.fasterxml.jackson.annotation.JsonProperty("cid")
                    private Integer cid;
                    @com.fasterxml.jackson.annotation.JsonProperty("cpy_attr0")
                    private Integer cpyAttr0;
                    @com.fasterxml.jackson.annotation.JsonProperty("hash_multitrack")
                    private String hashMultitrack;
                    @com.fasterxml.jackson.annotation.JsonProperty("cpy_grade")
                    private Integer cpyGrade;
                    @com.fasterxml.jackson.annotation.JsonProperty("musicpack_advance")
                    private Integer musicpackAdvance;
                    @com.fasterxml.jackson.annotation.JsonProperty("display")
                    private Integer display;
                    @com.fasterxml.jackson.annotation.JsonProperty("cpy_level")
                    private Integer cpyLevel;

                    @lombok.NoArgsConstructor
                    @lombok.Data
                    public static class ClassmapDTO {
                        @com.fasterxml.jackson.annotation.JsonProperty("attr0")
                        private Integer attr0;
                    }

                    @lombok.NoArgsConstructor
                    @lombok.Data
                    public static class QualitymapDTO {
                        @com.fasterxml.jackson.annotation.JsonProperty("attr0")
                        private Integer attr0;
                    }

                    @lombok.NoArgsConstructor
                    @lombok.Data
                    public static class IpmapDTO {
                        @com.fasterxml.jackson.annotation.JsonProperty("attr0")
                        private Long attr0;
                    }
                }
            }
        }
    }
}
