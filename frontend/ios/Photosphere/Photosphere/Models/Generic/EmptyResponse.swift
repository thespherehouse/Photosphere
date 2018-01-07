//
//  EmptyResponse.swift
//  Photosphere
//
//  Created by Soham Ray on 07/01/18.
//  Copyright © 2018 TheSphereHouse. All rights reserved.
//

import Foundation
import ObjectMapper

struct EmptyResponse : Mappable {
    
    init?(map: Map) {
        
    }
    
    mutating func mapping(map: Map) {
        
    }
}
