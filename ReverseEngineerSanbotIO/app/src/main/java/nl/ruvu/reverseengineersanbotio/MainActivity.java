package nl.ruvu.reverseengineersanbotio;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.unit.HardWareManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends TopBaseActivity {

    Button whiteLightBtn = (Button)findViewById(R.id.whiteLightButton);
    boolean isOpen = false;

    HardWareManager hardWareManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(128, 128);

        setBodyView(R.layout.activity_main);

        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);

        whiteLightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    whiteLightBtn.setText(R.string.close_light);
                    hardWareManager.switchWhiteLight(true);
                } else {
                    whiteLightBtn.setText(R.string.open_light);
                    hardWareManager.switchWhiteLight(false);
                }
                isOpen = !isOpen;
            }
        });
    }

    @Override
    protected void onMainServiceConnected() {
    }
}
