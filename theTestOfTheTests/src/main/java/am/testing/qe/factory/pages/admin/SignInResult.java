package am.testing.qe.factory.pages.admin;

public class SignInResult {

    static class Success extends SignInResult {
        public Success(AdminPageSignedIn adminPageSignedIn) {
            this.adminPageSignedIn = adminPageSignedIn;
        }

        AdminPageSignedIn adminPageSignedIn;
    }

    static class Failure extends SignInResult {
        public Failure(AdminPageSignedOut adminPageSignedOut) {
            this.adminPageSignedOut = adminPageSignedOut;
        }

        AdminPageSignedOut adminPageSignedOut;
    }

    public AdminPageSignedIn shouldBeSuccessful() {
        if(this instanceof Success){
            return ((Success) this).adminPageSignedIn;
        }
        throw new RuntimeException("SING IN IS NOT SUCCESSFUL");
    }

    public AdminPageSignedOut shouldBeFailure() {
        if(this instanceof Failure){
            return ((Failure) this).adminPageSignedOut;
        }
        throw new RuntimeException("SING IN IS SUCCESSFUL :/");
    }
}
