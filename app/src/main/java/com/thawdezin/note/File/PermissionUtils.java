package com.thawdezin.note.File;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionUtils {
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public static boolean neverAskAgainSelected(final Activity activity, final String permission) {
//        final boolean prevShouldShowStatus = getRationaleDisplayStatus(activity, permission);
//        final boolean currShouldShowStatus = activity.shouldShowRequestPermissionRationale(permission);
//        return prevShouldShowStatus != currShouldShowStatus;
//    }
//
//    public static void setShouldShowStatus(final Context context, final String permission) {
//        SharedPreferences genPrefs = context.getSharedPreferences("GENERIC_PREFERENCES", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = genPrefs.edit();
//        editor.putBoolean(permission, true);
//        editor.commit();
//    }
//
//    public static boolean getRationaleDisplayStatus(final Context context, final String permission) {
//        SharedPreferences genPrefs = context.getSharedPreferences("GENERIC_PREFERENCES", Context.MODE_PRIVATE);
//        return genPrefs.getBoolean(permission, false);
//    }

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime) {
        SharedPreferences sharedPreference = context.getSharedPreferences("GENERIC_PREFERENCES", Context.MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission) {
        return context.getSharedPreferences("GENERIC_PREFERENCES", Context.MODE_PRIVATE).getBoolean(permission, true);
    }

}
