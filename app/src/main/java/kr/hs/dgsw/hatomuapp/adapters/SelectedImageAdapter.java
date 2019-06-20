package kr.hs.dgsw.hatomuapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kr.hs.dgsw.hatomuapp.databinding.ItemPostingImageBinding;
import kr.hs.dgsw.hatomuapp.utils.FileUtil;

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.SelectItemViewHolder> {

    List<Uri> uriList;
    Context context;

    public SelectedImageAdapter(Context context, List<Uri> uriList) {
        this.context = context;
        this.uriList = uriList;
    }

    @NonNull
    @Override
    public SelectItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemPostingImageBinding binding = ItemPostingImageBinding.inflate(inflater, viewGroup, false);
        return new SelectItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectItemViewHolder selectItemViewHolder, int i) {
        selectItemViewHolder.bind(uriList.get(i));
        selectItemViewHolder.binding.imageView.setOnClickListener((v) -> {
            uriList.remove(i);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if (this.uriList == null)
            return 0;
        return this.uriList.size();
    }

    public void addItem(Uri uri) {
        this.uriList.add(uri);
        notifyDataSetChanged();
    }

    public class SelectItemViewHolder extends RecyclerView.ViewHolder {
        ItemPostingImageBinding binding;

        public SelectItemViewHolder(@NonNull ItemPostingImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Uri uri) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile(FileUtil.getPath(context, uri), options);
            binding.imageView.setImageBitmap(bitmap);
        }
    }

}
