//
//  CommonModels.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared

struct TypeModel: Hashable {
    var typeIcon: String
    var typeName: String
}

struct SlotModel: Hashable {
    var month: String
    var year: String
    var slotData: [TimeSlotData]
}
