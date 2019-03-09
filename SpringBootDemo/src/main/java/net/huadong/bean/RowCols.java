package net.huadong.bean;

import java.util.Date;

/**
 * @Author: wj
 * @Date: 2019/1/3 17:11
 * @Version 1.0
 */
public class RowCols {
    private String direction;
    private String latitude;
    private String longitude;

    private String machCod;

    private String speed;
    private Date time;
    private String matCod;


    public String getMatCod() {
        return matCod;
    }

    public void setMatCod(String matCod) {
        this.matCod = matCod;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMachCod() {
        return machCod;
    }

    public void setMachCod(String machCod) {
        this.machCod = machCod;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }




    @Override
    public String toString() {
        return "RowCols{" +
                "direction='" + direction + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", machCod='" + machCod + '\'' +
                ", speed='" + speed + '\'' +
                ", time=" + time +
                '}';
    }
}
