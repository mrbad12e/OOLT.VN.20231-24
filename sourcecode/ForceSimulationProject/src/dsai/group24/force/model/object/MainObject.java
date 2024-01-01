package dsai.group24.force.model.object;

import dsai.group24.force.model.vector.Force;
import dsai.group24.force.model.vector.HorizontalVector;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

// Lớp này chứa các thuộc tính và phương thức cho khối lượng, gia tốc dịch chuyển,vận tốc dịch chuyển và vị trí.
public abstract class MainObject {

	// Giá trị mặc định của khối lượng của lớp MainObject.
	public static final double DEFAULT_MASS = 50;
    // Thuộc tính khối lượng của MainObject.
	private DoubleProperty mass = new SimpleDoubleProperty(DEFAULT_MASS);
    // Thuộc tính gia tốc dịch chuyển của MainObject. Giá trị ban đầu là 0.0.
	private HorizontalVector acc = new HorizontalVector(0.0);
    // Thuộc tính vận tốc dịch chuyển của MainObject. Giá trị ban đầu là 0.0.
	private HorizontalVector vel = new HorizontalVector(0.0);
    // Thuộc tính vị trí của MainObject. Giá trị ban đầu là 0.0.
	private DoubleProperty pos = new SimpleDoubleProperty();

	// Phương thức trả về thuộc tính mass
	public DoubleProperty massProperty() {
		return mass;
	}

	// phương thức trả về giá trị của thuộc tính mass
	public double getMass() {
		return mass.get();
	}

	// Phương thức để thay đổi giá trị của mass .Nếu mass<=0 ném ra một ngoại lệ
	public void setMass(double mass) throws Exception {
		// mass must > 0
		if (mass <= 0) {
			throw new Exception("Object's mass must be > 0");
		} else {
			this.mass.setValue(mass);
		}
	}

	// Phương thức trả về thuộc tính gia tốc 
	public HorizontalVector accProperty() {
		return acc;
	}

	// Phương thức trả về thuộc tính vận tốc 
	public HorizontalVector velProperty() {
		return vel;
	}

	// Phương thức thay đổi gia tốc
	public void setAcc(double acc) {
		this.acc.setValue(acc);
	}

	// Phương thức thay đổi vận tốc
	public void setVel(double vel) {
		this.vel.setValue(vel);
	}

	// Phương thức cập nhật gia tốc dịch chuyển của MainObject khi có lực áp dụng lên nó
	public void updateAcc(Force force) {
		setAcc(force.getValue() / getMass());
	}

	// Phương thức cập nhật vận tốc dịch chuyển của MainObject sau khoảng thời gian t.
	public void updateVel(double t) {
		// old transitional velocity
		double oldVel = velProperty().getValue();
		// new transitional velocity
		double newVel = oldVel + accProperty().getValue() * t;
		if (oldVel * newVel < 0) {
			setVel(0);
		} else {
			setVel(newVel);
		}
	}

	// Áp dụng các lực trong khoảng thời gian t để cập nhật gia tốc dịch chuyển,vận tốc dịch chuyển và vị trí. 
	public void applyForceInTime(Force netforce, Force fForce, double t) {
		// old transitional velocity
		double oldVel = velProperty().getValue();
		// update transitional acceleration
		updateAcc(netforce);
		// update transitional velocity
		updateVel(t);
		// update position
		updatePos(oldVel, t);
	}

	// Trả về thuộc tính vị trí
	public DoubleProperty posProperty() {
		return pos;
	}

	// Trả về vị trí 
	public double getPos() {
		return pos.get();
	}

	// Thay đổi vị trí
	public void setPos(double pos) {
		this.pos.setValue(pos);
	}

	// Cập nhật vị trí
	public void updatePos(double oldVel, double t) {
		setPos(getPos() + oldVel * t + 0.5 * accProperty().getValue() * t * t);
	}

	//Constructor mặc định của lớp.
	public MainObject() {
	}

	// Constructor của lớp với khối lượng được chỉ định.
	public MainObject(double mass) throws Exception {
		setMass(mass);
	}

}
