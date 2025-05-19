package com.example.checkbox;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkbox.SecondActivity;

public class MainActivity extends AppCompatActivity {
    EditText edtten, editCMND, editBosung;
    CheckBox chkdocbao, chkdocsach, chkdoccode;
    Button btnsend;
    RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtten = findViewById(R.id.edtTen);
        editCMND = findViewById(R.id.editCMND);
        editBosung = findViewById(R.id.editBosung);

        chkdocbao = findViewById(R.id.chkdobao);
        chkdocsach = findViewById(R.id.chkdosach);
        chkdoccode = findViewById(R.id.chkdocode);

        btnsend = findViewById(R.id.btnsend);
        group = findViewById(R.id.idgroup);

        btnsend.setOnClickListener(v -> doShowInformation());

        // ✅ Xử lý khi nhấn nút Back bằng cách hiện SecondActivity
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                finish(); // nếu muốn đóng MainActivity
            }
        });
    }

    public void doShowInformation() {
        // Kiểm tra tên hợp lệ
        String ten = edtten.getText().toString().trim();
        if (ten.length() < 3) {
            edtten.requestFocus();
            edtten.selectAll();
            Toast.makeText(this, "Tên phải >= 3 ký tự", Toast.LENGTH_LONG).show();
            return;
        }

        // Kiểm tra CMND hợp lệ
        String cmnd = editCMND.getText().toString().trim();
        if (cmnd.length() != 9) {
            editCMND.requestFocus();
            editCMND.selectAll();
            Toast.makeText(this, "CMND phải đúng 9 ký tự", Toast.LENGTH_LONG).show();
            return;
        }

        // Kiểm tra bằng cấp
        String bang = "";
        int id = group.getCheckedRadioButtonId();
        if (id == -1) {
            Toast.makeText(this, "Phải chọn bằng cấp", Toast.LENGTH_LONG).show();
            return;
        }
        RadioButton rad = findViewById(id);
        bang = rad.getText().toString();

        // Kiểm tra sở thích
        String sothich = "";
        if (chkdocbao.isChecked()) sothich += chkdocbao.getText() + "\n";
        if (chkdocsach.isChecked()) sothich += chkdocsach.getText() + "\n";
        if (chkdoccode.isChecked()) sothich += chkdoccode.getText() + "\n";

        // Lấy thông tin bổ sung
        String bosung = editBosung.getText().toString();

        // Tạo nội dung thông báo
        String msg = ten + "\n";
        msg += cmnd + "\n";
        msg += bang + "\n";
        msg += sothich;
        msg += "------------------\n";
        msg += "Thông tin bổ sung:\n" + bosung + "\n";

        // Hiển thị Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin cá nhân");
        builder.setMessage(msg);
        builder.setPositiveButton("Đóng", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }
}
