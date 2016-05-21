package ruanjianbei.wifi.com.ViewPagerinfo.AppLoader.Adpater;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.io.File;
import java.util.List;

import ruanjianbei.wifi.com.ViewPagerinfo.AppLoader.InterfaceAppInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.AppLoader.AppFileInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;


/**
 * Created by zhanghang on 2016/5/17.
 */
public class AppSelectAdapter extends RecyclerView.Adapter<AppSelectAdapter.MyViewHolder>
{
    private Context context;
    private List<InterfaceAppInfo> list;
    public static List<String> mSelectAPP = FragmentChoose.getFileChoose();
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        onItemClickListener = listener;
    }

    public AppSelectAdapter(Context context, List<InterfaceAppInfo> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public AppSelectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(
            R.layout.view_app_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(final AppSelectAdapter.MyViewHolder holder,
            final int position)
    {
        holder.imageView.setImageBitmap(((BitmapDrawable) list.get(position)
                .getFileIcon()).getBitmap());

        InterfaceAppInfo info = list.get(position);
        AppFileInfo fileInfo = new AppFileInfo();
        fileInfo.name = info.getFileName();
        fileInfo.type = info.getFileType();
        fileInfo.size = new File(info.getFilePath()).length();
        fileInfo.path = info.getFilePath();

        if (FragmentChoose.selectedList.contains(fileInfo))
        {
            holder.app_choice.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.app_choice.setVisibility(View.GONE);
        }

        holder.appName.setText(list.get(position).getFileName());
        holder.appSize.setText(list.get(position).getFileSize());

        holder.itemLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onItemClickListener != null)
                {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemLayout, pos);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public InterfaceAppInfo getItem(int position)
    {
        return list.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        ImageView app_choice;
        TextView appName;
        TextView appSize;
        LinearLayout itemLayout;

        public MyViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.AppIcon);
            appName = (TextView) view.findViewById(R.id.AppName);
            appSize = (TextView) view.findViewById(R.id.AppSize);
            app_choice = (ImageView) view.findViewById(R.id.app_choice);
            itemLayout = (LinearLayout) view.findViewById(R.id.app_item_layout);
        }
    }
}
