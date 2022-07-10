package com.example.filemanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private File dir;
    private File[] listFiles;
    private FileAdapter fileAdapter;
    List fileData = new ArrayList<>();
    final String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String currentPath = sdPath;
    private static final int REQUEST_PERMISSION = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int PERMISSION_TOTAL = 2;
    private boolean[] selection;
    private boolean isFileManagerInitialized = false;

    private TextView pathFile;
    private ListView file_list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pathFile = findViewById(R.id.pathFile);
        file_list_view = findViewById(R.id.file_list_view);
    }


    @SuppressLint("NewApi")
    private boolean arePermissionDenied() {
            int p = 0;
            while(p<PERMISSION_TOTAL) {
                if(checkSelfPermission(PERMISSIONS[p]) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                p++;
        }
        return false;
    }

    private String nameOfPath(String path) {
        return path.substring(path.lastIndexOf('/')+1);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSION);
            return;
        }
        if(!isFileManagerInitialized) {
            dir = new File(currentPath);
            listFiles = dir.listFiles();
            pathFile.setText(nameOfPath(currentPath));
            final int numberOfFiles = listFiles.length;

            isFileManagerInitialized = true;

            for(int i=0;i< numberOfFiles;i++) {
                fileData.add(String.valueOf(nameOfPath(listFiles[i].getAbsolutePath())));
            }
            fileAdapter = new FileAdapter(fileData);
            file_list_view.setAdapter(fileAdapter);

            selection = new boolean[fileData.size()];
            file_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    selection[i] = !selection[i];
//                    fileAdapter.setSelection(selection);
                        if(listFiles[i].isDirectory()) {
                            currentPath += "/"+ nameOfPath(listFiles[i].getAbsolutePath());
                            refresh();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", listFiles[i]);
                            intent.setDataAndType(uri, "video/*");
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(intent);
                        }
                }
            });

            final Button backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(currentPath.equals(sdPath)) return;
                    String parentPath = currentPath.substring(0, currentPath.lastIndexOf('/'));
                    currentPath = parentPath;
                    Log.v("TAG", ""+parentPath);
                    refresh();
                }
            });
        }
    }

    public void refresh() {
        dir = new File(currentPath);
        listFiles = dir.listFiles();
        fileData.clear();
        if(listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                fileData.add(nameOfPath(listFiles[i].getAbsolutePath()));
            }
        }
        fileAdapter = new FileAdapter(fileData);
        file_list_view.setAdapter(fileAdapter);
        pathFile.setText(nameOfPath(currentPath));
        fileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if(arePermissionDenied()) {
                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            } else {
                onResume();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_folder:
                // write your code here
                final AlertDialog.Builder newFolderDialog = new AlertDialog.Builder(MainActivity.this);
                newFolderDialog.setTitle("New Folder");
                newFolderDialog.setMessage("Enter your new folder's name");
                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                newFolderDialog.setView(input);
                newFolderDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final File newFolder = new File(currentPath +"/"+input.getText());
                        Log.v("TAG",""+newFolder.getAbsolutePath());
                        if(!newFolder.exists()) {

                            Toast.makeText(MainActivity.this,newFolder.mkdirs() ? "Create new folder successfully!" : "Failed to create new folder", Toast.LENGTH_LONG);
                            Log.v("TAG","Create new folder successfully!");
                            refresh();
                        } else {
                            Toast.makeText(MainActivity.this,"Folder has existed!", Toast.LENGTH_LONG);
                            Log.v("TAG","Folder has existed!");
                        }
                    }
                });
                newFolderDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                newFolderDialog.show();
                return true;

            case R.id.new_text:
                // write your code here
                Toast msg2 = Toast.makeText(getApplicationContext(), "Menu 2", Toast.LENGTH_LONG);
                msg2.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}