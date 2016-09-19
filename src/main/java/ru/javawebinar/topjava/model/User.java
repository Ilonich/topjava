package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class User extends NamedEntity implements Comparable {

    protected String email;

    protected String password;

    protected boolean enabled = true;

    protected Date registered = new Date();

    protected Set<Role> roles;

    protected int caloriesPerDay = MealsUtil.DEFAULT_CALORIES_PER_DAY;

    public User() {
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, MealsUtil.DEFAULT_CALORIES_PER_DAY, true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
        this.enabled = enabled;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User (" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isEnabled() != user.isEnabled()) return false;
        if (getCaloriesPerDay() != user.getCaloriesPerDay()) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (!getRegistered().equals(user.getRegistered())) return false;
        return getRoles().equals(user.getRoles());

    }

    @Override
    public int hashCode() {
        int result = getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + (isEnabled() ? 1 : 0);
        result = 31 * result + getRegistered().hashCode();
        result = 31 * result + getRoles().hashCode();
        result = 31 * result + getCaloriesPerDay();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        User u = (User) o;
        return this.name.compareTo(u.getName());
    }
}
