package dsai.group24.force.model.vector;

import dsai.group24.force.model.object.Cube;
import dsai.group24.force.model.object.Cylinder;
import dsai.group24.force.model.object.MainObject;
import dsai.group24.force.model.surface.Surface;

// Lớp này đại diện cho lực ma sát giữa Bề Mặt (Surface) và MainObject.
// Nó là một loại Force (lực) và chứa các thuộc tính và phương thức 
// để tính toán giá trị của lực ma sát khi thay đổi Surface, MainObject và AppliedForce.
public class FrictionForce extends Force {

	
	// Lưu trữ đối tượng Surface hiện tại liên quan đến FrictionForce này
	private Surface surface;
	// Lưu trữ đối tượng MainObject hiện tại liên quan đến FrictionForce này
	private MainObject mainObj;
	// Lưu trữ đối tượng AppliedForce hiện tại liên quan đến FrictionForce này
	private AppliedForce aForce;
	// Giá trị gia tốc rơi tự do
	public static final double g = 10;
	
    // Ngưỡng vận tốc tối thiểu.
    // Dưới ngưỡng này, chỉ có lực ma sát tĩnh có tác dụng lên đối tượng Cube,
    // lực ma sát động học sẽ biến mất.
	public static final double VEL_THRESHOLD = 0.001;

	// Constructor với tham số giá trị của FrictionForce
	public FrictionForce(double value) {
		super(value);
	}

	//Constructor với các tham số giá trị FrictionForce
    // cũng như Surface, MainObject và AppliedForce hiện tại liên quan đến FrictionForce này
	public FrictionForce(double value, Surface surface, MainObject mainObj, AppliedForce aForce) {
		// init pseudo value
		super(value);
		this.surface = surface;
		this.mainObj = mainObj;
		this.aForce = aForce;
		// tính toán lại giá trị thực của FrictionForce này
		updateFrictionForce();
	}

	// Phương thức cập nhật giá trị lực ma sát dựa trên Surface, MainObject và AppliedForce hiện tại
	public void updateFrictionForce() {
		if (mainObj != null) {
			double direction = 0;
			// Tính lực pháp tuyến
			double normalForce = mainObj.getMass() * g;
			double aForceValue = Math.abs(aForce.getValue());

			// Tính hướng của FrcitionForce này theo chuyển tiếp của nó
            // vận tốc và lực tác dụng
            // Xét vận tốc chuyển tiếp của vật trước rồi đến lực tác dụng
            // trên đó
			if (mainObj.velProperty().getLength() != 0) {
				direction = (mainObj.velProperty().getDirection() == true) ? -1 : 1;
			} else {
				if (aForceValue == 0) {
					setValue(0);
					return;
				} else {
					direction = (aForce.getDirection() == true) ? -1 : 1;
				}
			}

			// Tính giá trị của FrictionForce này
			if (mainObj instanceof Cube) {
				// Xử lý trường hợp vận tốc của đối tượng = 0
				if (aForceValue <= surface.getStaCoef() * normalForce
						&& mainObj.velProperty().getLength() < VEL_THRESHOLD) {
					setValue(-aForce.getValue());
				} else {
					setValue(direction * surface.getKiCoef() * normalForce);
				}
			} else if (mainObj instanceof Cylinder) {
				if (aForceValue <= 3 * surface.getStaCoef() * normalForce && aForceValue > 0) {
					setValue(direction * aForceValue / 3);
				} else {
					// Khi lực tác dụng = 0, xét hệ số ma sát động
					setValue(direction * surface.getKiCoef() * normalForce);
				}
			}
		}
	}

	// Phương thức cập nhật đối tượng MainObject liên quan đến lực ma sát
	public void setMainObj(MainObject obj) {
		this.mainObj = obj;
		// Updates friction force when changing the MainObject relating to its
		updateFrictionForce();
	}

}
