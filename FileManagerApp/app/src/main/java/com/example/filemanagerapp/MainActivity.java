package com.example.filemanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements FileAdapter.FileInterface {

    private File dir;
    private File[] listFiles;
    private File selectedFile;
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
            fileAdapter = new FileAdapter(fileData, this);
            file_list_view.setAdapter(fileAdapter);
            registerForContextMenu(file_list_view);
            file_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(listFiles[i].isDirectory()) {
                            currentPath += "/"+ nameOfPath(listFiles[i].getAbsolutePath());
                            refresh();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", listFiles[i]);
                            String url = listFiles[i].getAbsolutePath();
                            if (url.contains(".doc") || url.contains(".docx")) {
                                // Word document
                                intent.setDataAndType(uri, "application/msword");
                            } else if (url.contains(".pdf")) {
                                // PDF file
                                intent.setDataAndType(uri, "application/pdf");
                            } else if (url.contains(".ppt") || url.contains(".pptx")) {
                                // Powerpoint file
                                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                            } else if (url.contains(".xls") || url.contains(".xlsx")) {
                                // Excel file
                                intent.setDataAndType(uri, "application/vnd.ms-excel");
                            } else if (url.contains(".zip") || url.contains(".rar")) {
                                // WAV audio file
                                intent.setDataAndType(uri, "application/x-wav");
                            } else if (url.contains(".rtf")) {
                                // RTF file
                                intent.setDataAndType(uri, "application/rtf");
                            } else if (url.contains(".wav") || url.contains(".mp3")) {
                                // WAV audio file
                                intent.setDataAndType(uri, "audio/x-wav");
                            } else if (url.contains(".gif")) {
                                // GIF file
                                intent.setDataAndType(uri, "image/gif");
                            } else if (url.contains(".jpg") || url.contains(".jpeg") || url.contains(".png")) {
                                // JPG file
                                intent.setDataAndType(uri, "image/jpeg");
                            } else if (url.contains(".txt")) {
                                // Text file
                                intent.setDataAndType(uri, "text/plain");
                            } else if (url.contains(".3gp") || url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")) {
                                // Video files
                                intent.setDataAndType(uri, "video/*");
                            } else {
                                intent.setDataAndType(uri, "*/*");;
                            }

                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            if (intent.resolveActivity(getPackageManager()) == null) {
                                Toast.makeText(getApplicationContext(), "Can not find any apps to open this file!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else{
                                startActivity(intent);
                            }
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
        fileAdapter = new FileAdapter(fileData, this);
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
                        if(input.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this,"Can not create a folder with empty name!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Log.v("TAG","this is a"+input.getText()+"xxx");
                        final File newFolder = new File(currentPath +"/"+input.getText());
                        if(!newFolder.exists()) {

                            Toast.makeText(MainActivity.this,newFolder.mkdirs() ? "Create new folder successfully!" : "Failed to create new folder", Toast.LENGTH_LONG).show();
                            refresh();
                        } else {
                            Toast.makeText(MainActivity.this,"Folder has existed!", Toast.LENGTH_LONG).show();
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
                final AlertDialog.Builder newTextDialog = new AlertDialog.Builder(MainActivity.this);
                newTextDialog.setTitle("New Text File");
                newTextDialog.setMessage("Enter your text file's name");
                final EditText newTextInput = new EditText(MainActivity.this);
                newTextInput.setInputType(InputType.TYPE_CLASS_TEXT);
                newTextDialog.setView(newTextInput);
                newTextDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(newTextInput.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this,"Can not create a text file with empty name!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            String newFileName = newTextInput.getText().toString()+".txt";
                            File dir = new File(currentPath);
                            File file = new File(dir, newFileName) ;
                            Log.v("TAG",file.getAbsolutePath());
                            file.createNewFile();
                            FileOutputStream writer = new FileOutputStream(file);
                            Toast.makeText(MainActivity.this, "Create new text file successfully!", Toast.LENGTH_LONG).show();
                            writer.flush();
                            writer.close();
                            refresh();
                        } catch(FileNotFoundException e) {
                            Toast.makeText(MainActivity.this, "Failed to create new text file!", Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                newTextDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                newTextDialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        ListView listView = (ListView) v;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectedFile = listFiles[acmi.position];
        if(selectedFile.isDirectory()) {
            // add menu items
            menu.add(0, v.getId(), 0, "Rename folder");
            menu.add(0, v.getId(), 0, "Delete folder");
        } else {
            // add menu items
            menu.add(0, v.getId(), 0, "Rename file");
            menu.add(0, v.getId(), 0, "Delete file");
            menu.add(0, v.getId(), 0, "Copy");
            menu.add(0, v.getId(), 0, "Move to");
        }

    }

    public String getFileExtension(File file) {
        String path = file.getAbsolutePath();
        return path.substring(path.lastIndexOf('.')+1,path.length());
    }

    public void renameFolderOrFile() {
        final AlertDialog.Builder renameDialog = new AlertDialog.Builder(MainActivity.this);
        renameDialog.setTitle("Rename");
        renameDialog.setMessage("Enter your new file/folder's name");
        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        if(!selectedFile.isDirectory()) {
            input.setText(nameOfPath(selectedFile.getAbsolutePath().substring(0, selectedFile.getAbsolutePath().lastIndexOf('.'))));
        } else {
            input.setText(nameOfPath(selectedFile.getAbsolutePath()));
        }
        renameDialog.setView(input);
        renameDialog.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(input.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this,"Can not rename a file/folder with empty name!", Toast.LENGTH_LONG).show();
                    return;
                }
                String oldPath = selectedFile.getAbsolutePath();
                String newFilePath = currentPath +"/"+input.getText() + (selectedFile.isDirectory() ? "" : "."+getFileExtension(selectedFile));
                final File newFile = new File(newFilePath);
                if(!newFile.exists()) {
                    Toast.makeText(MainActivity.this,selectedFile.renameTo(newFile) ? "Rename successfully!" : "Failed to rename this file/folder", Toast.LENGTH_LONG).show();
                    refresh();
                } else {
                    Toast.makeText(MainActivity.this,"File/Folder has existed!", Toast.LENGTH_LONG).show();
                    Log.v("TAG","File/Folder has existed!");
                }
            }
        });
        renameDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        renameDialog.show();
    }

    public void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

         fileOrDirectory.delete();
    }

    public void deleteFolder() {
        final AlertDialog.Builder deleteFolderDialog = new AlertDialog.Builder(MainActivity.this);
        deleteFolderDialog.setTitle("Delete folder");
        deleteFolderDialog.setMessage("Do you really want to delete this folder?");
        deleteFolderDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteRecursive(selectedFile);
                refresh();
                Toast.makeText(MainActivity.this, "Delete folder successfully!", Toast.LENGTH_LONG).show();
            }
        });
        deleteFolderDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        deleteFolderDialog.show();
    }

    public void deleteFile() {
        final AlertDialog.Builder deleteFileDialog = new AlertDialog.Builder(MainActivity.this);
        deleteFileDialog.setTitle("Delete file");
        deleteFileDialog.setMessage("Do you really want to delete this file?");
        deleteFileDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, selectedFile.delete() ? "Delete file successfully!" : "Failed to delete this file!", Toast.LENGTH_LONG).show();
                refresh();
            }
        });
        deleteFileDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        deleteFileDialog.show();
    }

    public void copy(File src, File dst) {
        try {
            FileInputStream inStream = new FileInputStream(src);
            FileOutputStream outStream = new FileOutputStream(dst);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void move(File src, File dst) {
        try {
            FileInputStream inStream = new FileInputStream(src);
            FileOutputStream outStream = new FileOutputStream(dst);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
            src.delete();
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final int FILE_COPY_SELECT_CODE = 0;
    private static final int FILE_MOVE_SELECT_CODE = 1;

    private void doBrowseCopyFile()  {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(Intent.createChooser(i, "Choose directory"), FILE_COPY_SELECT_CODE);
    }

    private void doBrowseMoveFile()  {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(Intent.createChooser(i, "Choose directory"), FILE_MOVE_SELECT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_COPY_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Log.d("Test", "" + sdPath + '/' + data.getData().getPath().substring(data.getData().getPath().indexOf(':')+1, data.getData().getPath().length()));
                    String destPath = sdPath + '/' + data.getData().getPath().substring(data.getData().getPath().indexOf(':')+1, data.getData().getPath().length()) + '/' + nameOfPath(selectedFile.getAbsolutePath());
                    File destFile = new File(destPath);
                    copy(selectedFile, destFile);
                    break;
                }
                break;
            case FILE_MOVE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Log.d("Test", "" + sdPath + '/' + data.getData().getPath().substring(data.getData().getPath().indexOf(':')+1, data.getData().getPath().length()));
                    String destPath = sdPath + '/' + data.getData().getPath().substring(data.getData().getPath().indexOf(':')+1, data.getData().getPath().length()) + '/' + nameOfPath(selectedFile.getAbsolutePath());
                    File destFile = new File(destPath);
                    move(selectedFile, destFile);
                    break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String title = item.getTitle().toString();

        if(title.equals("Rename folder")) {
            renameFolderOrFile();
        } else if(title.equals("Delete folder")) {
            deleteFolder();
        } else if(title.equals("Delete file")) {
            deleteFile();
        } else if(title.equals("Rename file")) {
            renameFolderOrFile();
        } else if(title.equals("Copy")) {
            doBrowseCopyFile();
        } else {
            doBrowseMoveFile();
        }
        return true;
    }

    @Override
    public Boolean isDirectory(int index) {
        return listFiles[index].isDirectory();
    }
}