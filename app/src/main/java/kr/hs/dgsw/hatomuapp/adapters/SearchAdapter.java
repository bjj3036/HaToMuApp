package kr.hs.dgsw.hatomuapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kr.hs.dgsw.hatomuapp.beans.UserBean;
import kr.hs.dgsw.hatomuapp.databinding.ItemPostPreviewBinding;
import kr.hs.dgsw.hatomuapp.databinding.ItemSearchBinding;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<UserBean> list;
    private SearchItemClickListener listener;

    public SearchAdapter(SearchItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSearchBinding binding = ItemSearchBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        binding.setListener(this.listener);
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.binding.setUser(list.get(i));
    }

    @Override
    public int getItemCount() {
        if (this.list == null)
            return 0;
        return this.list.size();
    }

    public void setList(List<UserBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        ItemSearchBinding binding;

        public SearchViewHolder(@NonNull ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface SearchItemClickListener {
        void onSearchItemClick(View v, UserBean user);
    }
}
