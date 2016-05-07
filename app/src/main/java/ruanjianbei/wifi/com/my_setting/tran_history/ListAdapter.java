package ruanjianbei.wifi.com.my_setting.tran_history;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ruanjianbei.wifi.com.shanchuang.R;


/**
 * Created by linankun1 on 2016/5/5.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext = null;//context是上下文对象
    private List<String[]> file_info = null;
    public ListAdapter(Context mContext,List<String[]> file_info) {
        this.mContext=mContext;
        this.file_info=file_info;
    }
    @Override
    public int getCount() {
        return file_info.size();//获取列表数量
    }

    @Override
    public Object getItem(int position) {
        return file_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //
        final String[] fileInfo = file_info.get(position);
        ViewHolder holder = null;
        if(view == null){
            //加载视图
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_tran_history, null);
            //获取布局中的控件
            holder = new ViewHolder();
            holder.tv_fileName = (TextView) view.findViewById(R.id.tv1);
            holder.tv_time = (TextView) view.findViewById(R.id.tv2);
            holder.iv = (ImageView) view.findViewById(R.id.biaoqing);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();//对上面的优化，不用重复加载耗时
        }
        holder.tv_fileName.setText(fileInfo[0]);
        holder.tv_time.setText(fileInfo[1]);
        if(fileInfo[2].equals("上传")){
            holder.iv.setImageResource(R.drawable.send);
        }else if(fileInfo[2].equals("下载")){
            holder.iv.setImageResource(R.drawable.receive);
        }
        else {
        }
        return view;
    }
    static class ViewHolder{
        TextView tv_fileName,tv_time;
        ImageView iv;
    }
}