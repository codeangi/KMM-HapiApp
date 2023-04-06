//
//  CommonViews.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import Lottie

struct ProgressBar: View {
    
    var systemWidth = UIScreen.main.bounds.width
    var currentProgress: CGFloat
    var totalProgress: CGFloat = 6.0
    
    var body: some View {
        Divider()
        Color(Color.customCyan.cgColor ?? CGColor(gray: 0, alpha: 0))
            .frame(width: (systemWidth - 32) * currentProgress / totalProgress, height: 3)
            .offset(y: -10)
    }
}

struct navBarBackButton: View {
    
    @State var viewModel: AppointmentViewModel
    
    var body: some View {
        Image(systemName: "chevron.left")
            .onTapGesture {
                viewModel.popScreen()
            }
    }
}

struct navBarCancelButton: View {
    
    @State var viewModel: AppointmentViewModel
    
    var body: some View {
        Text("CANCEL")
            .font(.body)
            .fontWeight(.semibold)
            .foregroundColor(Color.customCyan)
            .onTapGesture {
                viewModel.navigateToRoot()
            }
    }
}

struct LottieView: UIViewRepresentable {
    var lottieFile: String
    
    func makeUIView(context: UIViewRepresentableContext<LottieView>) -> UIView {
        let view = UIView(frame: .zero)
        
        let animationView = LottieAnimationView()
        animationView.contentMode = .scaleAspectFit
        animationView.loopMode = .playOnce

        animationView.animation = LottieAnimation.named(lottieFile)
        animationView.contentMode = .scaleAspectFit
        animationView.play()
        
        view.addSubview(animationView)
        
        animationView.translatesAutoresizingMaskIntoConstraints = false
        animationView.heightAnchor.constraint(equalTo: view.heightAnchor).isActive = true
        animationView.widthAnchor.constraint(equalTo: view.widthAnchor).isActive = true
        
        return view
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {
        
    }
    
}
