package com.devfill.checkman_mvp.list_declarations.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devfill.checkman_mvp.R;
import com.devfill.checkman_mvp.model_data.Declarations;

import java.util.List;


public class DeclarationsAdapter extends RecyclerView.Adapter<DeclarationsAdapter.MyViewHolder>  {

    private static final String LOG_TAG = "DecrlarationsAdapterTag";

    private static Context mContext;
    private Activity myActivity;
    private List<Declarations.Item> declarationsList;

    IDeclarationsAdapterListener iDeclarationsAdapterListener;

    public interface IDeclarationsAdapterListener {
        public void onClickItemListDeclarations(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name,place_work,position;
        private View card_view;

        public MyViewHolder(View v) {
            super(v);
            this.name = (TextView) v.findViewById(R.id.name);
            this.place_work = (TextView) v.findViewById(R.id.place_work);
            this.position = (TextView) v.findViewById(R.id.position);
            this.card_view = v.findViewById(R.id.card_view_declarations);

            Typeface typefaceR = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/UbuntuMono-R.ttf");
            this.place_work.setTypeface(typefaceR);
            this.position.setTypeface(typefaceR);
            this.name.setTypeface(typefaceR);

        }
    }


    public DeclarationsAdapter(Context context, Activity activity, List<Declarations.Item> list,IDeclarationsAdapterListener iDeclarationsAdapterListener) {

        mContext = context;
        myActivity = activity;
        declarationsList = list;
        this.iDeclarationsAdapterListener = iDeclarationsAdapterListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.declarations_card_view, viewGroup, false);
        return new MyViewHolder(v);

    }
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final Declarations.Item declarations = declarationsList.get(position);



        viewHolder.name.setText("П.І.П. : " + declarations.getLastname() + " " + declarations.getFirstname());

        if(declarations.getPlaceOfWork() != null){

            viewHolder.place_work.setText("Місце роботи : " + declarations.getPlaceOfWork());
        }
        else{

            viewHolder.place_work.setText("Місце роботи  не вказано");

        }

        if(declarations.getPosition() == null || declarations.getPosition().equals("ні")){

           viewHolder.position.setText("Посада : не вказана");
        }
        else{

           viewHolder.position.setText("Посада : " + declarations.getPosition());
        }

        // define an on click listener to open PlaybackFragment
        viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              iDeclarationsAdapterListener.onClickItemListDeclarations(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return declarationsList.size();
    }

}
