package com.samsung.multidevicecloud;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DevicePolicyManager mDPM;
    private ComponentName mComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComp = new ComponentName(this, DAReceiver.class);
    }

    public void browse(View view){
        Intent intent = new Intent(MainActivity.this, WebActivity.class);
        startActivity(intent);
    }

    public void exploit(View view) {
        if (mDPM.isAdminActive(mComp)) {
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(MainActivity.this);
            normalDialog.setTitle("确认操作");
            normalDialog.setMessage("恢复出厂设置为不可逆操作，是否继续？");
            normalDialog.setPositiveButton("恢复",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
                        }
                    });
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "已取消操作", Toast.LENGTH_SHORT).show();
                        }
                    });
            normalDialog.show();
        } else {
            Toast.makeText(getBaseContext(), "设备管理器未启用", Toast.LENGTH_SHORT).show();
        }
    }

    public void enable(View view) {
        if (!mDPM.isAdminActive(mComp)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComp);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "三星云需要设备管理器权限来为您提供更好的服务");
            startActivityForResult(intent, 0);
        } else {
            mDPM.removeActiveAdmin(mComp);
            Toast.makeText(getBaseContext(), "设备管理器已禁用", Toast.LENGTH_SHORT).show();
        }
    }
}
