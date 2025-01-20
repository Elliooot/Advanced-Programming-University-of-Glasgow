public class Vec2d {
    float x;
    float y;

    public Vec2d(float x, float y){
        this.x = x;
        this.y = y;
    }
    public Vec2d add(Vec2d that){
        return new Vec2d(this.x + that.x, this.y + that.y);
    }
    public Vec2d scale(float alpha){
        return new Vec2d(alpha * this.x, alpha * this.y);
    }
    public Vec2d minus(Vec2d that){
        Vec2d temp = that.scale(-1.0f);
        return this.add(temp);
    }
}