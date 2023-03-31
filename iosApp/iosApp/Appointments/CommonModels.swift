//
//  CommonModels.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

struct TypeModel: Hashable {
    var typeIcon: String
    var typeName: String
}

struct LocationsModel: Hashable {
    var place: String
    var address: String
    var availableOn: String
}

struct CareTeamModel: Hashable {
    var name: String
    var description: String
}
