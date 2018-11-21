package fall2018.csc207project.models;

import android.content.Context;

public class ScoreDataStreamImpl implements ScoreDataStream{
    private static final String GAMETOSCORESPATH = "GameToScores.ser";
    private GlobalDataStream dataStream;

    public ScoreDataStreamImpl(GlobalDataStream dataStream){
        this.dataStream = dataStream;
    }

    @Override
    public Object getScores(Object initData, Context context){
        return dataStream.getAndInit(initData, GAMETOSCORESPATH, context);
    }

    @Override
    public void saveScores(Object data, Context context) {
        dataStream.saveGlobalData(data, GAMETOSCORESPATH, context);
    }
}