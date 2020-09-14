package crawler

import (
	"reflect"
	"sort"
	"sync"
	"testing"
	"time"
)

func TestEventCenter(t *testing.T) {
	center := NewEventCenter()
	input := []string{"1", "2", "3"}

	var got []string
	m := sync.Mutex{}
	center.AddExecutor(func(a interface{}) {
		m.Lock()
		defer m.Unlock()
		got = append(got, a.(string))
	})

	var got1 []string
	m1 := sync.Mutex{}
	center.AddExecutor(func(a interface{}) {
		m1.Lock()
		defer m1.Unlock()
		got1 = append(got1, a.(string))
	})

	for _, s := range input {
		center.Publish(s)
	}

	time.Sleep(time.Millisecond)
	if !sliceEqual(got, input) {
		t.Errorf("input %v,but got %v", input, got)
	}
	if !sliceEqual(got1, input) {
		t.Errorf("input %v,but got %v", input, got1)
	}
}

func sliceEqual(a, b []string) bool {
	sort.Strings(a)
	sort.Strings(b)
	return reflect.DeepEqual(a, b)
}
