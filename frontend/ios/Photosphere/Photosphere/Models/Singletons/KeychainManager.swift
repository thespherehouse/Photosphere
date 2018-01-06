//
//  KeychainManager.swift
//  Photosphere
//
//  Created by Soham Ray on 31/12/17.
//  Copyright © 2017 TheSphereHouse. All rights reserved.
//

import Foundation
import KeychainSwift

class KeychainManager {
    static let shared = KeychainManager()
    
    var keychain = KeychainSwift()
    
    func getDeviceToken() -> String? {
        guard let token = keychain.get(KeychainConstants.deviceToken) else {
            return nil
        }
        return token
    }
}
