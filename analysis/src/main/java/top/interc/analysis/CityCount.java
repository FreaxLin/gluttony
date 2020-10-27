package top.interc.analysis;

public class CityCount implements Comparable {

    private String city;

    private Integer count;

    public CityCount(String city, Integer count) {
        this.city = city;
        this.count = count;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int compareTo(Object o) {
        if (this.count > ((CityCount) o).getCount()){
            return 1;
        }
        if (this.count < ((CityCount) o).getCount()){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return city + "的用户数为:" + count;
    }
}
