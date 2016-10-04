package liuxiaocong.com.youtubelistandplay;

/**
 * Created by LiuXiaocong on 10/4/2016.
 */
public class YoutubeModel {

    /**
     * id : 1xo3af_6_Jk
     * snippet : {"thumbnails":{"medium":{"url":"https://i.ytimg.com/vi/1xo3af_6_Jk/mqdefault.jpg"}},"title":"Teaser Trailer: Pirates of the Caribbean: Dead Men Tell No Tales"}
     */

    private String id;
    /**
     * thumbnails : {"medium":{"url":"https://i.ytimg.com/vi/1xo3af_6_Jk/mqdefault.jpg"}}
     * title : Teaser Trailer: Pirates of the Caribbean: Dead Men Tell No Tales
     */

    private SnippetBean snippet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SnippetBean getSnippet() {
        return snippet;
    }

    public void setSnippet(SnippetBean snippet) {
        this.snippet = snippet;
    }

    public static class SnippetBean {
        /**
         * medium : {"url":"https://i.ytimg.com/vi/1xo3af_6_Jk/mqdefault.jpg"}
         */

        private ThumbnailsBean thumbnails;
        private String title;

        public ThumbnailsBean getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(ThumbnailsBean thumbnails) {
            this.thumbnails = thumbnails;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static class ThumbnailsBean {
            /**
             * url : https://i.ytimg.com/vi/1xo3af_6_Jk/mqdefault.jpg
             */

            private MediumBean medium;

            public MediumBean getMedium() {
                return medium;
            }

            public void setMedium(MediumBean medium) {
                this.medium = medium;
            }

            public static class MediumBean {
                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
