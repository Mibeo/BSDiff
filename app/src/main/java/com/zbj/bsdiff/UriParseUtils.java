package com.zbj.bsdiff;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by bingjia.zheng on 2019/8/2.
 */

public class UriParseUtils {
    private static Uri getUriForFile(Context context, File file) {
        return FileProvider.getUriForFile(context, getFileProvider(context), file);
    }

    private static String getFileProvider(Context context) {
        return context.getApplicationInfo().packageName + ".fileprovider";
    }

    public static void installApk(Activity activity, File apkFile) {
        if (!apkFile.exists()) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Uri fileUri = getUriForFile(activity, apkFile);
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }

}
