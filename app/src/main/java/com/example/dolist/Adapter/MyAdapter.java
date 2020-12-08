package com.example.dolist.Adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dolist.DatabaseHandler;
import com.example.dolist.MainActivity;
import com.example.dolist.Model.ToDoModel;
import com.example.dolist.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private List<ToDoModel> list = new ArrayList<>();
    Context context;
    public MyAdapter(Context context,List<ToDoModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        ToDoModel txt = list.get(position);
        holder.addTxt.setText(txt.getTask());

        holder.rmvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    DatabaseHandler db = new DatabaseHandler(context);
                    ToDoModel item = list.get(pos);
                    list.remove(pos);
                    db.deleteTask(item);
                    notifyItemRemoved(pos);
                    Toast.makeText(context,"Deleted Successfully "+item.getTask(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView addTxt;
        private ImageButton rmvBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            addTxt = itemView.findViewById(R.id.itemTextId);
            rmvBtn = itemView.findViewById(R.id.taskRemoveBtn);
        }
    }


}
