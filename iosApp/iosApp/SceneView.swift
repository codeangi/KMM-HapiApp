import SwiftUI
import shared

struct SceneView: View {
    
    @ObservedObject var viewModel: LoginStatusViewModel
    
    var body: some View {
        VStack {
            NavigationView {
                if viewModel.userIdAvailable && viewModel.patientIdAvailable {
                    TabBarView()
                } else {
                    LoginScreen(viewModel: LoginViewModel())
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        SceneView(viewModel: LoginStatusViewModel())
    }
}
