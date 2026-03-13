package com.example.miniproject.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.miniproject.R;
import com.example.miniproject.model.PhongTro;

import java.util.List;

public class PhongTroAdapter extends RecyclerView.Adapter<PhongTroAdapter.ViewHolder> {

    private List<PhongTro> danhSach;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
        void onDetailClick(int position);
    }

    public PhongTroAdapter(List<PhongTro> danhSach, OnItemClickListener listener) {
        this.danhSach = danhSach;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phong_tro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhongTro phong = danhSach.get(position);

        holder.tvTenPhong.setText(phong.getTenPhong());
        holder.tvGiaThue.setText(String.format("%,.0f VNĐ/tháng", phong.getGiaThue()));

        if (phong.isDaThuê()) {
            holder.tvTinhTrang.setText("Đã thuê");
            holder.tvTinhTrang.setTextColor(Color.RED);
            holder.itemView.setBackgroundColor(Color.parseColor("#FFEBEE"));
        } else {
            holder.tvTinhTrang.setText("Còn trống");
            holder.tvTinhTrang.setTextColor(Color.parseColor("#2E7D32"));
            holder.itemView.setBackgroundColor(Color.parseColor("#E8F5E9"));
        }

        holder.btnSua.setOnClickListener(v -> {
            int adapterPos = holder.getAdapterPosition();
            if (listener != null && adapterPos != RecyclerView.NO_POSITION) {
                listener.onEditClick(adapterPos);
            }
        });

        holder.btnXoa.setOnClickListener(v -> {
            int adapterPos = holder.getAdapterPosition();
            if (listener != null && adapterPos != RecyclerView.NO_POSITION) {
                listener.onDeleteClick(adapterPos);
            }
        });

        holder.btnChiTiet.setOnClickListener(v -> {
            int adapterPos = holder.getAdapterPosition();
            if (listener != null && adapterPos != RecyclerView.NO_POSITION) {
                listener.onDetailClick(adapterPos);
            }
        });

        // Click toàn bộ item để mở màn hình sửa
        holder.itemView.setOnClickListener(v -> {
            int adapterPos = holder.getAdapterPosition();
            if (listener != null && adapterPos != RecyclerView.NO_POSITION) {
                listener.onEditClick(adapterPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSach.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenPhong, tvGiaThue, tvTinhTrang;
        Button btnSua, btnXoa, btnChiTiet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            tvTinhTrang = itemView.findViewById(R.id.tvTinhTrang);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnChiTiet = itemView.findViewById(R.id.btnChiTiet);
        }
    }
}
