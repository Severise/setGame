package a555.setgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardView extends View {
    public JSONObject attr;
    private Paint p = new Paint();

    public static List<CardView> clickedCards = new ArrayList<>();

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int s = -1, bg = -1, q = -1;
        String col = "0";
        try {
            col = this.attr.getString("color");
            q = this.attr.getInt("quantity");
            s = this.attr.getInt("shape");
            bg = this.attr.getInt("background");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        double y = 1. / (q + 2);
        int width = getWidth();
        int height = getHeight();
        for (int i = 1; i < q + 2; i++) {
            p.setColor(Color.parseColor(col));

            if (s == 0) {
                canvas.drawRect(width / 4, (float) (getHeight() * y) - getHeight() / 10,
                        3 * width / 4, (float) (height * y) + height / 10, p);
                if (bg == 1) {
                    p.setColor(Color.WHITE);
                    canvas.drawRect(width / 4 + 8, (float) (height * y) + height / 12 - 3,
                            3 * width / 4 - 8, (float) (height * y) - height / 12 + 3, p);
                }
                if (bg == 2) {
                    p.setColor(Color.GRAY);
                    canvas.drawRect(width / 4 + 8, (float) (height * y) + height / 12 - 3,
                            3 * width / 4 - 8, (float) (height * y) - height / 12 + 3, p);
                }
            }
            if (s == 1) {
                canvas.drawCircle(width / 2, (float) (height * y), width / 9, p);
                if (bg == 1) {
                    p.setColor(Color.WHITE);
                    canvas.drawCircle(width / 2, (float) (height * y), width / 11, p);
                }
                if (bg == 2) {
                    p.setColor(Color.GRAY);
                    canvas.drawCircle(width / 2, (float) (height * y), width / 11, p);
                }
            }
            if (s == 2) {
                drawTriangle(canvas, p, width / 2, (int) (height * y), width / 4);
                if (bg == 1) {
                    p.setColor(Color.WHITE);
                    drawTriangle(canvas, p, width / 2, (int) (height * y) + 4, width / 5 - 5);
                }
                if (bg == 2) {
                    p.setColor(Color.GRAY);
                    drawTriangle(canvas, p, width / 2, (int) (height * y) + 4, width / 5 - 5);
                }
            }
            y = y + (1. / (q + 2));
        }
    }

    public void drawTriangle(Canvas canvas, Paint paint, int x, int y, int width) {
        int halfWidth = width / 2;
        Path path = new Path();
        path.moveTo(x, y - halfWidth);
        path.lineTo(x - halfWidth, y + halfWidth);
        path.lineTo(x + halfWidth, y + halfWidth);
        path.lineTo(x, y - halfWidth);
        path.close();
        canvas.drawPath(path, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (!(boolean) this.attr.get("clicked")) {
                clickedCards.add(this);
                this.attr.put("clicked", true);
                this.setBackgroundColor(Color.parseColor("#ddbbee"));
            } else {
                clickedCards.remove(this);
                this.attr.put("clicked", false);
                this.setBackgroundColor(Color.WHITE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.invalidate();
        if (clickedCards.size() == 3)
            this.isSet();
        return super.onTouchEvent(event);
    }

    public void attrs(String col, int q, int s, int bg) throws JSONException {
        attr = new JSONObject();
        this.attr.put("color", col);
        this.attr.put("quantity", q);
        this.attr.put("shape", s);
        this.attr.put("background", bg);
        this.attr.put("clicked", false);
    }


    public void isSet() {
        GameActivity GA = (GameActivity) this.getContext();
        JSONArray CardsAttrs = new JSONArray();
        for (CardView c : CardView.clickedCards) {
            CardsAttrs.put(c.attr);
        }
        try {
            if (checkAttr(CardsAttrs, "color") && checkAttr(CardsAttrs, "quantity") && checkAttr(CardsAttrs, "shape") && checkAttr(CardsAttrs, "background")) {
                for (CardView c : CardView.clickedCards) {
                    GA.nextCard(c);
                }
            }
            for (CardView c : CardView.clickedCards) {
                c.setBackgroundColor(Color.WHITE);
                c.attr.put("clicked", false);
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        clickedCards.clear();
    }

    public boolean checkAttr(JSONArray Attrs, String name) throws JSONException {
        if (Attrs.getJSONObject(0).getString("color") == null || Attrs.getJSONObject(1).getString("color") == null || Attrs.getJSONObject(2).getString("color") == null)
            return false;
        if (Attrs.getJSONObject(0).getInt("shape") == -1 || Attrs.getJSONObject(0).getInt("shape") == -1 || Attrs.getJSONObject(0).getInt("shape") == -1)
            return false;
        return ((Attrs.getJSONObject(0).get(name).equals(Attrs.getJSONObject(1).get(name)) &&
                Attrs.getJSONObject(0).get(name).equals(Attrs.getJSONObject(2).get(name))) ||
                (!Attrs.getJSONObject(0).get(name).equals(Attrs.getJSONObject(1).get(name)) &&
                        !Attrs.getJSONObject(0).get(name).equals(Attrs.getJSONObject(2).get(name)) &&
                        !Attrs.getJSONObject(1).get(name).equals(Attrs.getJSONObject(2).get(name))));
    }
}
