package com.example.fpt_app.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fpt_app.Models.MessageModel;
import com.example.fpt_app.R;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private List<MessageModel> data;
    private Context context;

    public MessageAdapter(List<MessageModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {
        View view = _view;
        if (view == null){
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_msg_item, null);
            TextView textViewName = (TextView) view.findViewById(R.id.textViewMsg);
//            TextView textViewM = (TextView) view.findViewById(R.id.textMsg);
            ViewHolder holder = new ViewHolder(textViewName);


            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        MessageModel messageModel = (MessageModel) getItem(_i);
        holder.message.setText(messageModel.getMessage());
        if (messageModel.getFromMe()){
            holder.message.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            holder.message.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }


        return view;
    }

    private static class ViewHolder{
        final TextView message;

        public ViewHolder(TextView msg) {
            this.message = msg;
        }
    }
}
