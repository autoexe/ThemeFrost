package com.serjiosoft.themefrost.video_player;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.serjiosoft.themefrost.R;
import com.serjiosoft.themefrost.custom_views.JustAlertDialog;
import com.serjiosoft.themefrost.custom_views.QualityDialogView;
import com.serjiosoft.themefrost.exo_player.ExoPlayerActivity;
import com.serjiosoft.themefrost.preferences.SharedPrefsController;
import com.serjiosoft.themefrost.themefrost_api.models_api.Video;

/**
 * Created by autoexec on 01.03.2017.
 */

public final class VideoPlayerNavigator {

    private static final String MX_PACKAGE_1 = "com.mxtech.videoplayer.ad";
    private static final String MX_PACKAGE_2 = "com.mxtech.videoplayer.pro";
    private static final String YOUTUBE = "youtube";
    private static final String YOUTUBE_PACKAGE = "com.google.android.youtube";
    private static final String YOUTU_BE = "youtu.be";


    public static void showVideo(Context context, Video videoForShowing){
        if (videoForShowing.files != null && videoForShowing.files.isMP4Video()){
            showDialogQuality(context, videoForShowing);
        } else if (videoForShowing.player == null){
        } else {
            if ((videoForShowing.player.contains(YOUTUBE) || videoForShowing.player.contains(YOUTU_BE)) && isYouTubeInstalled(context)){
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(videoForShowing.player)));
            } else {
                WebViewPlayer.intent(context).mUrl(videoForShowing.player).start();
            }
        }
    }

    private static void showDialogQuality(final Context context, final Video videoForShowing) {
        final JustAlertDialog qualityDialog = new JustAlertDialog(context, null);
        QualityDialogView qualityDialogView = new QualityDialogView(context);
        qualityDialogView.setFiles(videoForShowing.files, new QualityDialogView.IQualityInterface() {
            @Override
            public void onQualityClick(String url, int quality) {
                qualityDialog.dismiss();
                /*if (VideoPlayerNavigator.isMXPlayerInstalled(context) && SharedPrefsController.getBoolFromPref(context, SharedPrefsController.KEY_ON_MX_PLAYER)){
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setPackage(VideoPlayerNavigator.getPackageMXPlayerInstalled(context));
                    intent.putExtra(OwnersFragment.TITLE_ARG, videoForShowing.title);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                    return;
                }*/

                //VideoPlayerActivity.intent(context).mQuality(quality).mVideoForPlaying(videoForShowing).start();
                ExoPlayerActivity.intent(context).mQuality(quality).mVideoForPlaying(videoForShowing).start();

            }
        });

        qualityDialog.title(context.getString(R.string.quality));
        qualityDialog.setView(qualityDialogView);
        qualityDialog.show();

    }


    public static boolean isYouTubeInstalled(Context context) {
        try {
            context.getPackageManager().getPackageInfo(YOUTUBE_PACKAGE, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }



    public static boolean isMXPlayerInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(MX_PACKAGE_1, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            try {
                pm.getPackageInfo(MX_PACKAGE_2, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e2) {
                return false;
            }
        }
    }

    public static String getPackageMXPlayerInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(MX_PACKAGE_1, PackageManager.GET_ACTIVITIES);
            return MX_PACKAGE_1;
        } catch (PackageManager.NameNotFoundException e) {
            try {
                pm.getPackageInfo(MX_PACKAGE_2, PackageManager.GET_ACTIVITIES);
                return MX_PACKAGE_2;
            } catch (PackageManager.NameNotFoundException e2) {
                return null;
            }
        }
    }

}
