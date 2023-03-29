//
//  EmptyView.swift
//  iosApp
//
//  Created by Swasthik K S on 28/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct EmptyView: View {
    
    @ObservedObject var viewModel: EmptyViewModel
    
    var body: some View {
        Text("\(viewModel.title) screen will be available soon")
    }
}

struct EmptyView_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView(viewModel: EmptyViewModel(title: "Test"))
    }
}
