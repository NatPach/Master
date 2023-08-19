export default class LoggedUser {
    constructor(userType, displayName, accessToken) {
        this.userType = userType;
        this.displayName = displayName;
        this.accessToken = accessToken;
    }
    isDoctor() {
        return this.userType === 'doctor';
    }

    isPatient() {
        return this.userType === 'patient';
    }
}