package ruanjianbei.wifi.com.Phone_P_3G.download;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ruanjianbei.wifi.com.Phone_P_3G.download.Service.DownloadService;
import ruanjianbei.wifi.com.Phone_P_3G.download.entities.FileInfo;
import ruanjianbei.wifi.com.Phone_P_3G.upload.util.RoundProgressBarWidthNumber;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/4/23.
 */
public class FileListAdapter extends BaseAdapter {
    private Context mContext = null;//context是上下文对象
    private List<FileInfo> mFileList = null;
    public FileListAdapter(Context mContext,List<FileInfo> mFileList) {
        this.mContext=mContext;
        this.mFileList=mFileList;
    }
    @Override
    public int getCount() {
        return mFileList.size();//获取列表数量
    }

    @Override
    public Object getItem(int position) {
        return mFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //
        final FileInfo fileInfo = mFileList.get(position);
        ViewHolder holder = null;
        if(view == null){
            //加载视图
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_download_list, null);
            //获取布局中的控件
            holder = new ViewHolder();
            holder.tv_fileName = (TextView) view.findViewById(R.id.tv_fileName);
            holder.pb_progress1 = (RoundProgressBarWidthNumber) view.findViewById(R.id.pb_progress1);
            holder.pb_progress = (ProgressBar) view.findViewById(R.id.pb_progress);
            holder.btn_start = (Button) view.findViewById(R.id.btn_start);
            holder.btn_stop = (Button) view.findViewById(R.id.btn_stop);
            holder.tv_size = (TextView) view.findViewById(R.id.tv_size);
            holder.tv_fileName.setText(fileInfo.gettrueName());
            holder.tv_size.setText(fileInfo.getFilesize());
            holder.pb_progress.setMax(100);
            holder.btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 通过Intent传递参数给Service
                    Intent intent = new Intent(mContext, DownloadService.class);
                    intent.setAction(DownloadService.ACTION_START);
                    intent.putExtra("fileInfo", fileInfo);
                    mContext.startService(intent);
                    Toast.makeText(mContext, "开始下载:" + fileInfo.gettrueName(), Toast.LENGTH_SHORT).show();
                }
            });
            holder.btn_stop.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 通过Intent传递参数给Service
                    Intent intent = new Intent(mContext, DownloadService.class);
                    intent.setAction(DownloadService.ACTION_STOP);
                    intent.putExtra("fileInfo", fileInfo);
                    mContext.startService(intent);
                    Toast.makeText(mContext, "下载暂停:"+fileInfo.getFileName(), Toast.LENGTH_SHORT).show();
                }
            });
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();//对上面的优化，不用重复加载耗时
        }
        holder.pb_progress.setProgress(fileInfo.getFinished());
        holder.pb_progress1.setProgress(fileInfo.getFinished());
        return view;
    }
    //更新列表项中的进度条
    public void updateProgress(int id,int progress){
        FileInfo fileInfo = mFileList.get(id);
        fileInfo.setFinished(progress);
        notifyDataSetChanged();
    }
    static class ViewHolder{
        TextView tv_fileName,tv_size;
        RoundProgressBarWidthNumber pb_progress1;
        ProgressBar pb_progress;
        Button btn_stop,btn_start;
    }
}
