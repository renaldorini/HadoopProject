package cs455.RecordUtil;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.hadoop.io.Writable;

public class MedianRentRecord extends Record {

    public final List<String> RENT_LIST = new ArrayList<String>(Arrays.asList("< 100",
																			"100 - 150",
																			"150 - 199",
																			"200 - 249",
																			"250 - 299",
																			"300 - 349",
																			"350 - 399",
																			"400 - 449",
																			"450 - 499",
																			"500 - 549",
																			"550 - 599",
																			"600 - 649",
																			"650 - 699",
																			"700 - 749",
																			"750 - 999",
																			"1000 >"));

    private Map<String, Long> rentMap;

    public MedianRentRecord() {
        this.rentMap = new LinkedHashMap<String, Long>();
		for (String priceRange: RENT_LIST) {
			rentMap.put(priceRange, 0L);
		}
    }
	public List<String> getRentList() {
		return RENT_LIST;
	}

    public Map<String, Long> getMap() {
        return rentMap;
    }

    public void setMap(Map<String, Long> rentMap) {
        this.rentMap = rentMap;
    }

    @Override
    public String toString() {
        long total = 0;
        for (Long value : rentMap.values()) {
            total += value;
        }

        long medianValue = total / 2;
        long sum = 0;
		// Attempt to pass median value. If it doesn't then last value in RENT_LIST is returned
        for (Map.Entry<String, Long> entry : rentMap.entrySet()) {
			sum += entry.getValue();
            if (sum >=  medianValue) {
                return entry.getKey();
            }
        }

        return RENT_LIST.get(RENT_LIST.size() - 1);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        for (Long value : rentMap.values()) {
            dataOutput.writeLong(value);
        }
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        for (String priceRange : rentMap.keySet()) {
            rentMap.put(priceRange, dataInput.readLong());
        }
    }
}
