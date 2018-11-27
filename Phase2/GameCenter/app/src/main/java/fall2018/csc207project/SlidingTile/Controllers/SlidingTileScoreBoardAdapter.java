package fall2018.csc207project.SlidingTile.Controllers;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import fall2018.csc207project.Controllers.GameListViewAdapter;
import fall2018.csc207project.R;
import fall2018.csc207project.SlidingTile.Models.TileScore;

public class SlidingTileScoreBoardAdapter extends BaseAdapter {

    private List<TileScore> slidingTileTopScores;
    private Context mContext;
//    private LayoutInflater mLayoutInflater = null;
    private String[] strings = new String[2];


    public SlidingTileScoreBoardAdapter(List<TileScore> list, Context context) {
        mContext = context;
        slidingTileTopScores = list;
//        mLayoutInflater = (LayoutInflater) mContext
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return slidingTileTopScores.size();
    }
    @Override
    public Object getItem(int pos) {
        strings[0] = slidingTileTopScores.get(pos).user;
        strings[1] = String.valueOf(slidingTileTopScores.get(pos).value);
        return new String[] {slidingTileTopScores.get(pos).user,
                             String.valueOf(slidingTileTopScores.get(pos).value)};
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        MyViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.tile_game_score_board_row, parent, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
//            LayoutInflater li = (LayoutInflater) mContext
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            assert li != null;
//            view = li.inflate(R.layout.tile_game_score_board_row, null);
//            viewHolder = new MyViewHolder(view);
//            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        viewHolder.userName.setText(slidingTileTopScores.get(position).user);
        viewHolder.userScore.setText(String.valueOf(slidingTileTopScores.get(position).value));
        return view;
//        if(convertView == null){
//            LayoutInflater inflater = LayoutInflater.from(this.mContext);
//            convertView = inflater.inflate(R.layout.tile_game_score_board_row, parent, false);
//        }
//        final String [] item = (String[]) this.getItem(position);
//        ((TextView) convertView.findViewById(R.id.scoreBoardUser)).setText(item[0]);
//        ((TextView) convertView.findViewById(R.id.scoreBoardScore)).setText(item[1]);
//        return convertView;

    }

    class MyViewHolder{
        TextView userName;
        TextView userScore;
        MyViewHolder(View base) {
            userName = base.findViewById(R.id.scoreBoardUser);
            userScore = base.findViewById(R.id.scoreBoardScore);
        }
    }
}



