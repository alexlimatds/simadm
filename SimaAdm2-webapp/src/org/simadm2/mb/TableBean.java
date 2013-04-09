package org.simadm2.mb;
/*package mb;



import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
//import org.primefaces.examples.domain.Car;
import java.util.*;

public class TableBean implements Serializable {
    
    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("model", "manufacturer", "year", "color");

	private final static String[] colors;
	
	private final static String[] manufacturers;

    private String columnTemplate = "model manufacturer year";

	static {
		colors = new String[10];
		colors[0] = "Black";
		colors[1] = "White";
		colors[2] = "Green";
		colors[3] = "Red";
		colors[4] = "Blue";
		colors[5] = "Orange";
		colors[6] = "Silver";
		colors[7] = "Yellow";
		colors[8] = "Brown";
		colors[9] = "Maroon";
		
		manufacturers = new String[10];
		manufacturers[0] = "Mercedes";
		manufacturers[1] = "BMW";
		manufacturers[2] = "Volvo";
		manufacturers[3] = "Audi";
		manufacturers[4] = "Renault";
		manufacturers[5] = "Opel";
		manufacturers[6] = "Volkswagen";
		manufacturers[7] = "Chrysler";
		manufacturers[8] = "Ferrari";
		manufacturers[9] = "Ford";
	}

    private List<Car> filteredCars;

	private List<Car> carsSmall;

    private List<ColumnModel> columns = new ArrayList<ColumnModel>();;

	public TableBean() {
		carsSmall = new ArrayList<Car>();
		
		populateRandomCars(carsSmall, 9);

        createDynamicColumns();
	}
	
	private void populateRandomCars(List<Car> list, int size) {
		for(int i = 0 ; i < size ; i++)
			list.add(new Car(getRandomModel(), getRandomYear(), getRandomManufacturer(), getRandomColor()));
	}
	
	public List<Car> getCarsSmall() {
		return carsSmall;
	}

	private int getRandomYear() {
		return (int) (Math.random() * 50 + 1960);
	}
	
	private String getRandomColor() {
		return colors[(int) (Math.random() * 10)];
	}
	
	private String getRandomManufacturer() {
		return manufacturers[(int) (Math.random() * 10)];
	}
    
	private String getRandomModel() {
		return UUID.randomUUID().toString().substring(0, 8);
	}
	
    public List<ColumnModel> getColumns() {
        return columns;
    }

    public String[] getManufacturers() {
        return manufacturers;
    }

    public String[] getColors() {
        return colors;
    }

    public List<Car> getFilteredCars() {
        return filteredCars;
    }

    public void setFilteredCars(List<Car> filteredCars) {
        this.filteredCars = filteredCars;
    }
    
    static public class ColumnModel implements Serializable {

        private String header;
        private String property;

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }
    
    public String getColumnTemplate() {
        return columnTemplate;
    }
    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }
    
    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:cars");
        table.setValueExpression("sortBy", null);
        
        //update columns
        createDynamicColumns();
    }
    
    public void createDynamicColumns() {
        String[] columnKeys = columnTemplate.split(" ");
        columns.clear();      
        
        for(String columnKey : columnKeys) {
            String key = columnKey.trim();
            
            if(VALID_COLUMN_KEYS.contains(key)) {
                columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
            }
        }
    }
}
*/
				
