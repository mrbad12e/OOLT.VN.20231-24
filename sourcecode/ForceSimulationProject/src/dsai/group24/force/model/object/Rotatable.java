package dsai.group24.force.model.object;

import dsai.group24.force.model.vector.Force;
import javafx.beans.property.DoubleProperty;

// Chứa các phương thức để qunr lí thông tin về đối tượng có thể xoay
public interface Rotatable {

	// Trả về thuộc tính angAcc(gia tốc góc)
	DoubleProperty angAccProperty();

	// Trả về giá trị gia tốc góc của đối tượng 
	double getAngAcc();

	// Thiết lập gia tốc góc của đối tượng
	void setAngAcc(double angAcc);

	// Cập nhật gia tốc góc khi có lực tác động lên đối tượng và gây ra xoay
	void updateAngAcc(Force force);

	// Trả về thuộc tính angVel (Vận tốc góc)
	DoubleProperty angVelProperty();

	// Trả về giá trị vận tốc góc của đối tượng.
	double getAngVel();

	// Thiết lập vận tốc góc cho đối tượng.
	void setAngVel(double angVel);

	// Cập nhật vận tốc góc sau một khoảng thời gian t.
	void updateAngVel(double t);

	// Trả về thuộc tính angle (Góc quay)
	DoubleProperty angleProperty();

	// Trả về giá trị góc quay của đối tượng.
	double getAngle();

	//Thiết lập góc quay cho đối tượng.
	void setAngle(double angle);

	//Cập nhật góc quay sau một khoảng thời gian t dựa trên vận tốc góc cũ và thời gian.
	void updateAngle(double oldAngVel, double t);

	//Trả về thuộc tính radius (Bán kính)
	DoubleProperty radiusProperty();

	// Trả về giá trị bán kính của đối tượng.
	double getRadius();

	//Thiết lập bán kính cho đối tượng. Có thể ném ngoại lệ nếu giá trị bán kính không hợp lệ.
	void setRadius(double radius) throws Exception;

	//Áp dụng một lực trong một khoảng thời gian t để cập nhật gia tốc góc, vận tốc góc và góc quay của đối tượng
	void applyForceInTimeRotate(Force force, double t);

}
