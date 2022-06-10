package com.example.mailapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MailItemAdapter.ItemClickInterface {
    List<String> senderMail = Arrays.asList("son.dd194362@sis.hust.edu.vn",
            "minh.nv195732@sis.hust.edu.vn", "duc.ht191221@sis.hust.edu.vn",
            "manh.hd194163@sis.hust.edu.vn", "nam.dp194335@sis.hust.edu.vn",
            "dung.hv194100@sis.hust.edu.vn", "thang.vv194395@sis.hust.edu.vn");
    List<String> title = Arrays.asList("Hello", "Nice", "New assignment",
            "New meeting", "Borrow your homework","Next exam","Return your bike");
    List<String> content = Arrays.asList("Nice to meet you 1","Nice to meet you 2",
            "Nice to meet you 3","Nice to meet you 4","Nice to meet you 5",
            "Nice to meet you 6", "Nice to meet you 7");
    List<String> time = Arrays.asList("11:54 PM", "8:45 AM", "3:12 PM", "5:12 AM", "4:34 PM","8:23 PM", "10:10 AM");
    List<MailModel> items = new ArrayList<MailModel>();
    private void generateValue()
    {
        int min = 0;
        int max = senderMail.size()-1;
        for(int i=1;i<=30;i++)
        {
            int random_int;
            random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            String newSenderMail = senderMail.get(random_int);
            random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            String newTitle = title.get(random_int);
            random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            String newContent = content.get(random_int);
            random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            String newTime = time.get(random_int);

            MailModel newMailModel = new MailModel(newSenderMail, newTitle,
                    newContent, newTime, false);
            items.add(newMailModel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateValue();
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        MailItemAdapter adapter = new MailItemAdapter(items,this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void OnInterestClick(int position, Boolean state)
    {
        items.get(position).setInterested(state);
    }
}