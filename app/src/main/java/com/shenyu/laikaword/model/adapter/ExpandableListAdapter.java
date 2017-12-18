package com.shenyu.laikaword.model.adapter;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenyu_zxjCode on 2017/12/14 0014.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private List<String> groupListData=new ArrayList<>();
    private Map<String,List<String>> paramMap = new LinkedHashMap<>();
    private String type="";
    public ExpandableListAdapter(Map<String,List<String>> map, String type){
       if (map!=null){
           this.paramMap=map;
           this.type=type;
           for (Map.Entry<String, List<String>> entry : map.entrySet()) {
               groupListData.add(entry.getKey());
           }
       }

    }
    @Override
    public int getGroupCount() {
        return groupListData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return paramMap.get(groupListData.get(groupPosition))==null?0:paramMap.get(groupListData.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupListData.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view=null;
        GroupHolder groupholder = null;
        if(convertView!=null){
            view = convertView;
            groupholder = (GroupHolder) view.getTag();
        }else{
            view = View.inflate(UIUtil.getContext(), R.layout.group_item, null);
            groupholder =new GroupHolder();
            groupholder.GrouptextView=view.findViewById(R.id.tv_zhuan_count);
            groupholder.imageView =view.findViewById(R.id.iv_arrow_more);
            groupholder.layout = view.findViewById(R.id.ly_zhuanmai);
            view.setTag(groupholder);
        }
        groupholder.GrouptextView.setText(groupListData.get(groupPosition));
        groupholder.layout.setVisibility(groupListData.get(groupPosition).equals("转卖状态: 转卖完成!")?View.GONE:View.VISIBLE);
        if (isExpanded)
            ObjectAnimator.ofFloat( groupholder.imageView , "rotation", 0, 180).start();
        else
            ObjectAnimator.ofFloat( groupholder.imageView , "rotation", -180, 0).start();
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view=null;
        ChildHolder childholder = null;
        if(convertView!=null){
            view = convertView;
            childholder = (ChildHolder) view.getTag();
        }else{
            view = View.inflate(UIUtil.getContext(),R.layout.child_item, null);
            childholder = new ChildHolder();
            childholder.childText = view.findViewById(R.id.tv_resel_code);
            view.setTag(childholder);

        }
        childholder.childText.setText(paramMap.get(groupListData.get(groupPosition)).get(childPosition));
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

     class GroupHolder{
        TextView GrouptextView;
        ImageView imageView;
        LinearLayout layout;
    }

     class ChildHolder{
        TextView childText;
    }
}
