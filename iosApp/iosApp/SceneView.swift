import SwiftUI
import shared

struct SceneView: View {
	

	var body: some View {
        VStack {
            NavigationView{
                LoginScreen(viewModel: LoginViewModel())
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		SceneView()
	}
}
