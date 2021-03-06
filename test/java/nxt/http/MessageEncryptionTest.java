/******************************************************************************
 * Copyright © 2013-2016 The Nxt Core Developers.                             *
 *                                                                            *
 * See the AUTHORS.txt, DEVELOPER-AGREEMENT.txt and LICENSE.txt files at      *
 * the top-level directory of this distribution for the individual copyright  *
 * holder information and the developer policies on copyright and licensing.  *
 *                                                                            *
 * Unless otherwise agreed in a custom licensing agreement, no part of the    *
 * Nxt software, including this file, may be copied, modified, propagated,    *
 * or distributed except according to the terms contained in the LICENSE.txt  *
 * file.                                                                      *
 *                                                                            *
 * Removal or modification of this copyright notice is prohibited.            *
 *                                                                            *
 ******************************************************************************/

package nxt.http;

import nxt.Account;
import nxt.BlockchainTest;
import nxt.crypto.EncryptedData;
import nxt.util.Convert;
import org.json.simple.JSONStreamAware;
import org.junit.Assert;
import org.junit.Test;

public class MessageEncryptionTest extends BlockchainTest {

    @Test
    public void encryptBytes() {
        byte[] data = { (byte)0x01, (byte)0x02, (byte)0xF1, (byte)0xF2 };
        EncryptedData encryptedData = encrypt(data);
        Assert.assertArrayEquals(data, decrypt(encryptedData));
    }

    @Test
    public void encryptText() {
        JSONStreamAware json = JSONResponses.INCORRECT_ARBITRARY_MESSAGE;
        EncryptedData encryptedData = encrypt(Convert.toBytes(json.toString()));
        Assert.assertEquals(json.toString(), Convert.toString(decrypt(encryptedData)));
    }

    @Test
    public void encryptEmpty() {
        EncryptedData encryptedData = encrypt(Convert.toBytes(""));
        Assert.assertEquals("", Convert.toString(decrypt(encryptedData)));
    }

    private EncryptedData encrypt(byte[] data) {
        Account recipient = Account.getAccount(BOB.getPublicKey());
        if (recipient == null) {
            throw new IllegalStateException();
        }
        return recipient.encryptTo(data, ALICE.getSecretPhrase(), false);
    }

    private byte[] decrypt(EncryptedData encryptedData) {
        Account sender = ALICE.getAccount();
        return sender.decryptFrom(encryptedData, BOB.getSecretPhrase(), false);
    }

}
