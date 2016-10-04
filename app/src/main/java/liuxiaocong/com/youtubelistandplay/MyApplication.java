package liuxiaocong.com.youtubelistandplay;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by LiuXiaocong on 8/15/2016.
 */
//{"itemList":{"ECZCommon":true,"EJianshu":false,"EMinSheng":true,"EJieyang":true,"EShantou":true},"sourceList":{"ECZCommonSourceArray":[{"url":"http://3g.czbtv.com/wapczxw/","titleListFilter":"#czxw a","titleSubFilter":"","targetUrlFromTileFilter":"","targetUrlDomain":"http://3g.czbtv.com/wapczxw/","targetContentFilter":"#czxw"}],"EMinShengSourceArray":[{"url":"http://www.chaozhoudaily.com/index.php/news/cate/pcid/1/cid/14.html","titleListFilter":"#main .main_content tr","titleSubFilter":"td:eq(1)","targetUrlFromTileFilter":"td:eq(1) a","targetUrlDomain":"http://www.chaozhoudaily.com/","targetContentFilter":"#main .news_content"}],"EJieyangSourceArray":[{"url":"http://www.jynews.net/Category_182/index.aspx","titleListFilter":"#right_caitou_v8 table table table table tr a","titleSubFilter":"","targetUrlFromTileFilter":"","targetUrlDomain":"http://www.jynews.net/","targetContentFilter":"span.STYLE666"}],"EShantouSourceArray":[{"url":"http://dahuawang.com/gundong/showdetail1.asp?CNo=1101","titleListFilter":".newsList li a","titleSubFilter":"","targetUrlFromTileFilter":"","targetUrlDomain":"http://dahuawang.com/","targetContentFilter":"#Content .content"}]}}
//
//


public class MyApplication extends Application {
    private final String TAG = "MyApplication";
    private static MyApplication instance;


    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
