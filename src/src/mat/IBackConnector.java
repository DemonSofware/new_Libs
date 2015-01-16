package mat;

public interface IBackConnector {
	mat.Matt getSlots(String username, mat.Matt matt);//return updated matt
	void sendInvitation(String userName, String name, String tableName, String[] contacts);
}
