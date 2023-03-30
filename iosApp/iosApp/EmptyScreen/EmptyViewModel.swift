//
//  EmptyViewModel.swift
//  iosApp
//
//  Created by Swasthik K S on 28/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

class EmptyViewModel: ObservableObject {
    @Published var title: String = ""
    
    init(title: String) {
        self.title = title
    }
}

