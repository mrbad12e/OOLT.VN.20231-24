package dsai.group24.force.model.vector;

public class Force extends HorizontalVector {

	// Phương thức khởi tạo Force với giá trị cho lực được truyền vào.
	public Force(double value) {
		super(value);
	}

    // Trả về giá trị tổng hai lực f1,f2
	public static Force sumTwoForce(Force f1, Force f2) {
		// Tạo Lực mới có giá trị = tổng đại số của các giá trị f1 và f2
		Force netForce = new Force(f1.getValue() + f2.getValue());
		// cập nhật hướng lực
		netForce.updateValueDirection();
		return netForce;
	}

}
