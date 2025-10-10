package ru.ifmo.se.weblab.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@ManagedBean(name="clockBean", eager = true)
@ViewScoped
public class ClockBean implements Serializable {
    private double hourAngle;
    private double minuteAngle;

    public ClockBean() {
        updateTime();
    }

    public void updateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        double hours = calendar.get(Calendar.HOUR);
        double minutes = calendar.get(Calendar.MINUTE);
        double seconds = calendar.get(Calendar.SECOND);

        this.hourAngle = (hours + minutes / 60) * 30;
        this.minuteAngle = (minutes + seconds / 60) * 6;
    }

    public double getHourAngle() {
        return hourAngle;
    }

    public double getMinuteAngle() {
        return minuteAngle;
    }
}
