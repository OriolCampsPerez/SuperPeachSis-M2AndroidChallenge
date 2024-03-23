package helloandroid.ut3.challengeandroid.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class HitBox {
    public Rect rect;
    public float radius;

    public HitBox(Rect rect, float radius) {
        this.rect = rect;
        this.radius = radius;
    }

    /**
     * Draw the HitBox on the canvas for debugging purposes
     *
     * @param canvas the canvas to draw on
     */
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        // draw the arch on top (semicircle)
        canvas.drawArc(new RectF(rect.left, rect.top, rect.right, rect.top + 2 * radius), 180, 180, false, paint);
        // draw the arch on bottom (semicircle)
        canvas.drawArc(new RectF(rect.left, rect.bottom - 2 * radius, rect.right, rect.bottom), 0, 180, false, paint);

        // draw the left line
        canvas.drawLine(rect.left, rect.top + radius, rect.left, rect.bottom - radius, paint);
        // draw the right line
        canvas.drawLine(rect.right, rect.top + radius, rect.right, rect.bottom - radius, paint);

        paint.setColor(Color.BLUE);
        canvas.drawRect(rect, paint);

        // biggest rect which fits inside the HitBox
        Rect innerRect = new Rect(rect.left, rect.top + (int) radius, rect.right, rect.bottom - (int) radius);
        paint.setColor(Color.RED);
        canvas.drawRect(innerRect, paint);
    }

    public boolean intersect(HitBox other) {
        // check if the HitBoxes are too far apart to intersect
        double distanceBetweenCenters = Math.sqrt(Math.pow(this.rect.centerX() - other.rect.centerX(), 2) + Math.pow(this.rect.centerY() - other.rect.centerY(), 2));
        if (distanceBetweenCenters > this.radius + other.radius) {
            return false;
        }

        // check if inner rectangles intersect
        Rect innerRect1 = new Rect(this.rect.left, this.rect.top + (int) this.radius, this.rect.right, this.rect.bottom - (int) this.radius);
        Rect innerRect2 = new Rect(other.rect.left, other.rect.top + (int) other.radius, other.rect.right, other.rect.bottom - (int) other.radius);
        if (innerRect1.intersect(innerRect2)) {
            return true;
        }

        // check if the semicircles intersect
        float centerX1 = rect.centerX();
        float centerY1_top = rect.top + radius;
        float centerY1_bottom = rect.bottom - radius;

        float centerX2 = other.rect.centerX();
        float centerY2_top = other.rect.top + other.radius;
        float centerY2_bottom = other.rect.bottom - other.radius;

        // calculate the distance between the centers of the HitBoxes
        double distance_top = Math.sqrt(Math.pow(centerX2 - centerX1, 2) + Math.pow(centerY2_top - centerY1_top, 2));
        double distance_bottom = Math.sqrt(Math.pow(centerX2 - centerX1, 2) + Math.pow(centerY2_bottom - centerY1_bottom, 2));

        // check if the distance between the centers is less than the sum of the radii
        if (distance_top <= this.radius + other.radius || distance_bottom <= this.radius + other.radius) {
            return true;
        }

        return false;
    }


}
