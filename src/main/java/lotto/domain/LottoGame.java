package lotto.domain;

import static lotto.domain.LottoProfit.getReturnRate;
import static lotto.domain.Lottos.generateLottos;

import java.util.List;

public class LottoGame {
    LottoResult lottoResult;
    InputChecker inputChecker;

    public LottoGame() {
        lottoResult = new LottoResult();
        inputChecker = new InputChecker();
    }

    public void start() {
        long userLottoPrice = inputChecker.readLottoPrice();
        long lottoCount = lottoResult.lottoNumbersPurchased(userLottoPrice);
        printLottoCountPurchased(lottoCount);

        // 로또(들) 생성
        Lottos purchasedLottos = generateLottos(lottoCount);

        // 당첨,보너스 번호 입력
        List<Integer> winningNumbers = inputChecker.readWinningNumbers();
        Integer bonusNumber = inputChecker.readBonusNumber();

        purchasedLottos.printLottos(); // #

        // 통계 출력
        lottoResult.calculateRewardStatistics(purchasedLottos,winningNumbers,bonusNumber);

        // 수익률 출력
        printReturnRate(purchasedLottos);
        printRewardStatistics();
    }

    private void printReturnRate(Lottos purchasedLottos) {
        long totalReward = lottoResult.getTotalReward();
        double returnRate = getReturnRate(purchasedLottos.count(), totalReward);
        System.out.println("총 수익률은 " + String.format("%.1f%%", returnRate * 100) + "입니다.");
    }
    private void printRewardStatistics(){
        String[] rewardNames = lottoResult.getRewardNames();
        int[] reward = lottoResult.getReward();
        int [] rewardMatch = lottoResult.getRewardMatch();

        for (int i = 0; i < rewardMatch.length; i++) {
            System.out.println(rewardNames[i] + " 일치 (" + String.format("%,d원", reward[i]) + ") - " + rewardMatch[i] + "개");
        }
    }

    private void printLottoCountPurchased(long lottoCount) {
        System.out.println(lottoCount + "개를 구매했습니다.");
    }

}
