//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
///**
// * Created by fkruege on 4/13/17.
// */
//public class EventBusTest {
//
//    public static void main(String[] args) throws InterruptedException {
//
//        EventBusListener listener = new EventBusListener();
//        listener.run();
//
//        MessageEvent msg = new MessageEvent();
//        msg.number = 1;
//
//        while (true) {
//            EventBus.getDefault().post(msg);
//            Thread.sleep(500);
//
//
//        }
//
//    }
//
//
//    public static class EventBusListener implements Runnable {
//
//        @Subscribe
//        public void onMessageEvent(MessageEvent event) {
//            System.out.println("Name: " + Thread.currentThread().getName() + " Event is: " + event.number);
//            /* Do something */
//        }
//
//
//        @Override
//        public void run() {
//            EventBus.getDefault().register(this);
//
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//    }
//
//    public static class MessageEvent {
//        int number = 0;
//    }
//
//}
