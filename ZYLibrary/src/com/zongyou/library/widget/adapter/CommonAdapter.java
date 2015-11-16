package com.zongyou.library.widget.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 为所有ListView\GridView等提供通用adapter
 *
 * @author Altas
 * @date 2014年9月2日
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas;
    protected int mItemLayoutId;

    public CommonAdapter(Context context, List<T> datas, int itemLayoutId) {
        super();
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);

        this.mDatas = datas;
        if (datas == null) {
            this.mDatas = new ArrayList<T>();
        }
        this.mItemLayoutId = itemLayoutId;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(List<T> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        this.position = position;
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    /**
     * 为item设置值
     *
     * @param helper
     * @param item
     */
    public abstract void convert(ViewHolder helper, T item, int position);

    protected int position;

    /**
     * 获取viewholder
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

}
