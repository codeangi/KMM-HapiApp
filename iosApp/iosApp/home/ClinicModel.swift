//
//  ClinicModel.swift
//  iosApp
//
//  Created by Swasthik K S on 28/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ClinicModel:Hashable {
    var name: String
    var address: Address
}

struct Address: Hashable {
    var addressLine1: String
    var city: String
    var id: String
}
